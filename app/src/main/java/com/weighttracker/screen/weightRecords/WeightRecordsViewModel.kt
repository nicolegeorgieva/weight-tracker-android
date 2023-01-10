package com.weighttracker.screen.weightRecords

import com.weighttracker.base.SimpleFlowViewModel
import com.weighttracker.persistence.database.weightrecords.DeleteWeightRecordAct
import com.weighttracker.persistence.database.weightrecords.WeightRecordEntity
import com.weighttracker.persistence.database.weightrecords.WeightRecordsFlow
import com.weighttracker.persistence.database.weightrecords.WriteWeightRecordAct
import com.weighttracker.persistence.datastore.kgselected.KgSelectedFlow
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import javax.inject.Inject

@HiltViewModel
class WeightRecordsViewModel @Inject constructor(
    private val weightRecordsFlow: WeightRecordsFlow,
    private val writeWeightRecordAct: WriteWeightRecordAct,
    private val deleteWeightRecordAct: DeleteWeightRecordAct,
    private val kgSelectedFlow: KgSelectedFlow
) : SimpleFlowViewModel<WeightRecordsState, WeightRecordsEvent>() {
    override val initialUi = WeightRecordsState(
        //while loading
        weightRecords = emptyList(),
        weightUnit = "kg"
    )

    override val uiFlow: Flow<WeightRecordsState> = combine(
        weightRecordsFlow(Unit),
        kgSelectedFlow(Unit)
    ) { weightRecords, kgSelected ->
        WeightRecordsState(
            weightRecords = weightRecords
                .sortedByDescending { it.dateTime }
                .map { record ->
                    WeightRecordEntity(
                        id = record.id,
                        dateTime = record.dateTime,
                        weightKg = if (kgSelected) {
                            record.weightKg
                        } else {
                            record.weightKg * 2.205
                        }
                    )
                },
            weightUnit = if (kgSelected) "kg" else "lb"
        )
    }

    override suspend fun handleEvent(event: WeightRecordsEvent) {
        when (event) {
            is WeightRecordsEvent.DeleteWeightRecord -> {
                deleteWeightRecordAct(event.record.id)
            }
            is WeightRecordsEvent.UpdateWeightRecord -> {
                writeWeightRecordAct(event.newRecord)
            }
            else -> {}
        }
    }
}