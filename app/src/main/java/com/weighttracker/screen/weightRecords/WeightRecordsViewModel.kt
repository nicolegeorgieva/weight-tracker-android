package com.weighttracker.screen.weightRecords

import com.weighttracker.base.SimpleFlowViewModel
import com.weighttracker.domain.calculateBmi
import com.weighttracker.domain.convertToKg
import com.weighttracker.persistence.database.weightrecords.DeleteWeightRecordAct
import com.weighttracker.persistence.database.weightrecords.WeightRecordEntity
import com.weighttracker.persistence.database.weightrecords.WeightRecordsFlow
import com.weighttracker.persistence.database.weightrecords.WriteWeightRecordAct
import com.weighttracker.persistence.datastore.height.HeightFlow
import com.weighttracker.persistence.datastore.kgselected.KgSelectedFlow
import com.weighttracker.persistence.datastore.mselected.MSelectedFlow
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import javax.inject.Inject

@HiltViewModel
class WeightRecordsViewModel @Inject constructor(
    private val weightRecordsFlow: WeightRecordsFlow,
    private val writeWeightRecordAct: WriteWeightRecordAct,
    private val deleteWeightRecordAct: DeleteWeightRecordAct,
    private val kgSelectedFlow: KgSelectedFlow,
    private val heightFlow: HeightFlow,
    private val mSelectedFlow: MSelectedFlow,

    ) : SimpleFlowViewModel<WeightRecordsState, WeightRecordsEvent>() {
    override val initialUi = WeightRecordsState(
        //while loading
        weightRecords = emptyList(),
        weightUnit = "kg",
        latestWeight = 0.0,
        startWeight = 0.0,
        difference = 0.0
    )

    override val uiFlow: Flow<WeightRecordsState> = combine(
        weightRecordsFlow(Unit),
        kgSelectedFlow(Unit),
        heightFlow(Unit),
        mSelectedFlow(Unit)
    ) { weightRecords, kgSelected, height, mSelected ->
        val latestWeight = weightRecords.maxByOrNull { it.dateTime }?.weightInKg
        val startWeight = weightRecords.minByOrNull { it.dateTime }?.weightInKg

        WeightRecordsState(
            weightRecords = weightRecords
                .sortedByDescending { it.dateTime }
                .map { record ->
                    WeightRecordWithBmi(
                        id = record.id,
                        date = record.dateTime,
                        weightInKg = convertToKg(
                            weight = record.weightInKg,
                            kgSelected = kgSelected
                        ),
                        bmi = if (height != null) {
                            calculateBmi(
                                weight = record.weightInKg, height = height,
                                kgSelected = kgSelected, mSelected = mSelected
                            )
                        } else null
                    )
                },
            weightUnit = if (kgSelected) "kg" else "lb",
            latestWeight = startWeight,
            startWeight = startWeight,
            difference = if (startWeight != null && latestWeight != null) {
                if ((startWeight - latestWeight) < 0) {
                    latestWeight - startWeight
                } else {
                    startWeight - latestWeight
                }
            } else null
        )
    }

    override suspend fun handleEvent(event: WeightRecordsEvent) {
        when (event) {
            is WeightRecordsEvent.DeleteWeightRecord -> {
                deleteWeightRecordAct(event.record.id)
            }
            is WeightRecordsEvent.UpdateWeightRecord -> {
                val entity = WeightRecordEntity(
                    id = event.newRecord.id,
                    dateTime = event.newRecord.date, weightInKg = event.newRecord.weightInKg
                )
                writeWeightRecordAct(entity)
            }
            else -> {}
        }
    }
}