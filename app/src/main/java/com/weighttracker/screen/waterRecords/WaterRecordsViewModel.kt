package com.weighttracker.screen.waterRecords

import com.weighttracker.base.SimpleFlowViewModel
import com.weighttracker.persistence.database.waterrecords.DeleteWaterRecordAct
import com.weighttracker.persistence.database.waterrecords.WaterRecordsFlow
import com.weighttracker.persistence.database.waterrecords.WriteWaterRecordAct
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import javax.inject.Inject

@HiltViewModel
class WaterRecordsViewModel @Inject constructor(
    private val waterRecordsFlow: WaterRecordsFlow,
    private val writeWaterRecordAct: WriteWaterRecordAct,
    private val deleteWaterRecordAct: DeleteWaterRecordAct
) : SimpleFlowViewModel<WaterRecordsState, WaterRecordsEvent>() {
    override val initialUi = WaterRecordsState(
        //while loading
        waterRecords = emptyList()
    )

    override val uiFlow: Flow<WaterRecordsState> = combine(
        waterRecordsFlow(Unit),
        waterRecordsFlow(Unit),

        ) { waterRecords, _ ->
        WaterRecordsState(
            waterRecords = waterRecords
        )
    }

    override suspend fun handleEvent(event: WaterRecordsEvent) {
        when (event) {
            is WaterRecordsEvent.DeleteWaterRecord -> {
                deleteWaterRecordAct(event.record.id)
            }
            is WaterRecordsEvent.UpdateWaterRecord -> {
                writeWaterRecordAct(event.newRecord)
            }
            else -> {}
        }
    }
}