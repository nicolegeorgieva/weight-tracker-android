package com.weighttracker.screen.weightRecords

import com.weighttracker.base.SimpleFlowViewModel
import com.weighttracker.domain.calculateBmi
import com.weighttracker.domain.convertWeight
import com.weighttracker.domain.data.Height
import com.weighttracker.domain.data.Weight
import com.weighttracker.domain.data.WeightUnit
import com.weighttracker.persistence.database.weightrecords.DeleteWeightRecordAct
import com.weighttracker.persistence.database.weightrecords.WeightRecordEntity
import com.weighttracker.persistence.database.weightrecords.WeightRecordsFlow
import com.weighttracker.persistence.database.weightrecords.WriteWeightRecordAct
import com.weighttracker.persistence.datastore.height.HeightFlow
import com.weighttracker.persistence.datastore.height.HeightUnitFlow
import com.weighttracker.persistence.datastore.weight.WeightUnitFlow
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import javax.inject.Inject

@HiltViewModel
class WeightRecordsViewModel @Inject constructor(
    private val weightRecordsFlow: WeightRecordsFlow,
    private val weightUnitFlow: WeightUnitFlow,
    private val writeWeightRecordAct: WriteWeightRecordAct,
    private val deleteWeightRecordAct: DeleteWeightRecordAct,
    private val heightFlow: HeightFlow,
    private val heightUnitFlow: HeightUnitFlow

    ) : SimpleFlowViewModel<WeightRecordsState, WeightRecordsEvent>() {
    override val initialUi = WeightRecordsState(
        //while loading
        weightRecords = emptyList(),
        weightUnit = "kg",
        latestWeight = 0.0,
        startWeight = 0.0,
        difference = 0.0,
        latestBmi = 0.0,
        startBmi = 0.0
    )

    override val uiFlow: Flow<WeightRecordsState> = combine(
        weightRecordsFlow(Unit),
        weightUnitFlow(Unit),
        heightFlow(Unit),
        heightUnitFlow(Unit)
    ) { weightRecords, weightUnit, height, heightUnit ->
        val weightRecordsWithBmi = weightRecords
            .sortedByDescending { it.dateTime }
            .map { record ->
                WeightRecordWithBmi(
                    id = record.id,
                    date = record.dateTime,
                    weight = convertWeight(
                        Weight(record.weightInKg, WeightUnit.Kg),
                        weightUnit
                    ),
                    bmi = if (height != null) {
                        calculateBmi(
                            weight = Weight(record.weightInKg, WeightUnit.Kg),
                            height = Height(height.value, heightUnit)
                        )
                    } else null
                )
            }

        val latestWeight = weightRecords.maxByOrNull { it.dateTime }?.weightInKg
            ?.let {
                convertWeight(
                    Weight(it, WeightUnit.Kg),
                    weightUnit
                )
            }

        val startWeight = weightRecords.minByOrNull { it.dateTime }?.weightInKg
            ?.let {
                convertWeight(
                    Weight(it, WeightUnit.Kg),
                    weightUnit
                )
            }

        val latestBmi = weightRecordsWithBmi.maxByOrNull { it.date }?.bmi

        val startBmi = weightRecordsWithBmi.minByOrNull { it.date }?.bmi

        WeightRecordsState(
            weightRecords = weightRecordsWithBmi,
            weightUnit = when (weightUnit) {
                WeightUnit.Kg -> "kg"
                WeightUnit.Lb -> "lb"
            },
            latestWeight = latestWeight?.value,
            startWeight = startWeight?.value,
            difference = if (startWeight != null && latestWeight != null)
                startWeight.value - latestWeight.value
            else null,
            latestBmi = latestBmi,
            startBmi = startBmi
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
                    dateTime = event.newRecord.date,
                    weightInKg = convertWeight(event.newRecord.weight, WeightUnit.Kg).value
                )
                writeWeightRecordAct(entity)
            }
            else -> {}
        }
    }
}