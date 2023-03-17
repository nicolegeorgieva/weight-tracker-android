package com.weighttracker.screen.waterRecords

import com.weighttracker.base.SimpleFlowViewModel
import com.weighttracker.domain.convertWater
import com.weighttracker.domain.data.Water
import com.weighttracker.domain.data.WaterUnit
import com.weighttracker.persistence.database.waterrecords.DeleteWaterRecordAct
import com.weighttracker.persistence.database.waterrecords.WaterRecordsFlow
import com.weighttracker.persistence.database.waterrecords.WriteWaterRecordAct
import com.weighttracker.persistence.datastore.lselected.LSelectedFlow
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import javax.inject.Inject

@HiltViewModel
class WaterRecordsViewModel @Inject constructor(
    private val waterRecordsFlow: WaterRecordsFlow,
    private val writeWaterRecordAct: WriteWaterRecordAct,
    private val deleteWaterRecordAct: DeleteWaterRecordAct,
    private val lSelectedFlow: LSelectedFlow
) : SimpleFlowViewModel<WaterRecordsState, WaterRecordsEvent>() {
    override val initialUi = WaterRecordsState(
        //while loading
        waterRecords = emptyList(),
        waterUnit = "l"
    )

    override val uiFlow: Flow<WaterRecordsState> = combine(
        waterRecordsFlow(Unit),
        lSelectedFlow(Unit)
    ) { waterRecords, lSelected ->
        WaterRecordsState(
            waterRecords = waterRecords.sortedByDescending {
                it.dateTime
            }.map {
                it.copy(
                    water = convertWater(
                        Water(
                            it.water, WaterUnit.L
                        ),
                        toUnit = if (lSelected) WaterUnit.L else WaterUnit.Gal
                    ).value
                )
            },
            waterUnit = if (lSelected) "l" else "gal"
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