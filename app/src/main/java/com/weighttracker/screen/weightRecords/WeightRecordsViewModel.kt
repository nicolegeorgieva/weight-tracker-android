package com.weighttracker.screen.weightRecords

import com.weighttracker.base.SimpleFlowViewModel
import com.weighttracker.persistence.database.weightrecords.DeleteWeightRecordAct
import com.weighttracker.persistence.database.weightrecords.WeightRecordsFlow
import com.weighttracker.persistence.database.weightrecords.WriteWeightRecordAct
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import javax.inject.Inject

@HiltViewModel
class WeightRecordsViewModel @Inject constructor(
    private val weightRecordsFlow: WeightRecordsFlow,
    private val writeWeightRecordAct: WriteWeightRecordAct,
    private val deleteWeightRecordAct: DeleteWeightRecordAct
) : SimpleFlowViewModel<WeightRecordsState, WeightRecordsEvent>() {
    override val initialUi = WeightRecordsState(
        //while loading
        weightRecords = emptyList()
    )

    override val uiFlow: Flow<WeightRecordsState> = combine(
        weightRecordsFlow(Unit),
        weightRecordsFlow(Unit),
    ) { weightRecords, _ ->
        WeightRecordsState(
            weightRecords = weightRecords.sortedByDescending { it.dateTime }
        )
    }

    override suspend fun handleEvent(event: WeightRecordsEvent) {
        when (event) {
            is WeightRecordsEvent.DeleteWeightRecord -> {
                deleteWeightRecordAct(event.record.id)
            }
            else -> {}
        }
    }
}