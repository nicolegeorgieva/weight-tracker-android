package com.weighttracker.screen.bmi

import com.weighttracker.base.SimpleFlowViewModel
import com.weighttracker.combine
import com.weighttracker.domain.*
import com.weighttracker.domain.data.*
import com.weighttracker.persistence.database.activityrecords.ActivityRecordEntity
import com.weighttracker.persistence.database.activityrecords.WriteActivityRecordAct
import com.weighttracker.persistence.database.waterrecords.WaterRecordEntity
import com.weighttracker.persistence.database.waterrecords.WriteWaterRecordAct
import com.weighttracker.persistence.database.weightrecords.WeightRecordEntity
import com.weighttracker.persistence.database.weightrecords.WriteWeightRecordAct
import com.weighttracker.persistence.datastore.activity.ActivityFlow
import com.weighttracker.persistence.datastore.activity.WriteActivityAct
import com.weighttracker.persistence.datastore.height.HeightFlow
import com.weighttracker.persistence.datastore.height.HeightUnitFlow
import com.weighttracker.persistence.datastore.height.WriteHeightAct
import com.weighttracker.persistence.datastore.quote.QuoteFlow
import com.weighttracker.persistence.datastore.water.WaterFlow
import com.weighttracker.persistence.datastore.water.WaterUnitFlow
import com.weighttracker.persistence.datastore.water.WriteWaterAct
import com.weighttracker.persistence.datastore.weight.WeightFlow
import com.weighttracker.persistence.datastore.weight.WeightUnitFlow
import com.weighttracker.persistence.datastore.weight.WriteWeightAct
import com.weighttracker.toUtc
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import java.time.LocalDateTime
import java.util.*
import javax.inject.Inject

@HiltViewModel
class BmiViewModel @Inject constructor(
    private val weightFlow: WeightFlow,
    private val weightUnitFlow: WeightUnitFlow,
    private val writeWeightAct: WriteWeightAct,
    private val heightFlow: HeightFlow,
    private val writeHeightAct: WriteHeightAct,
    private val heightUnitFlow: HeightUnitFlow,
    private val quoteFlow: QuoteFlow,
    private val writeWeightRecordAct: WriteWeightRecordAct,
    private val activityFlow: ActivityFlow,
    private val writeActivityAct: WriteActivityAct,
    private val writeActivityRecordAct: WriteActivityRecordAct,
    private val waterFlow: WaterFlow,
    private val writeWaterAct: WriteWaterAct,
    private val writeWaterRecordAct: WriteWaterRecordAct,
    private val waterUnitFlow: WaterUnitFlow
) : SimpleFlowViewModel<BmiState, BmiEvent>() {
    override val initialUi = BmiState(
        weightValue = null,
        weightUnit = WeightUnit.Kg,
        heightValue = 0.0,
        heightUnit = HeightUnit.M,
        bmi = 0.0,
        quote = "",
        normalWeightRange = null,
        activity = "",
        waterValue = 0.0,
        waterUnit = WaterUnit.L,
        glasses = emptyList()
    )

    override val uiFlow: Flow<BmiState> = combine(
        weightFlow(Unit),
        weightUnitFlow(Unit),
        heightFlow(Unit),
        heightUnitFlow(Unit),
        quoteFlow(Unit),
        activityFlow(Unit),
        waterFlow(Unit),
        waterUnitFlow(Unit)
    ) { weight, weightUnit, height, heightUnit, quote, activity, water, waterUnit ->
        BmiState(
            weightValue = weight?.value,
            weightUnit = weightUnit,
            heightValue = height?.value,
            bmi = if (weight != null && height != null && weight.value > 0 && height.value > 0) {
                calculateBmi(
                    weight,
                    height
                )
            } else null,
            heightUnit = heightUnit,
            quote = quote,
            activity = activity,
            waterValue = water?.value,
            normalWeightRange = if (height != null && weight != null) {
                calculateNormalWeightRange(
                    height,
                    weight
                )
            } else {
                null
            },
            waterUnit = waterUnit,
            glasses = glasses(water?.value ?: 0.0)
        )
    }

    override suspend fun handleEvent(event: BmiEvent) {
        when (event) {
            is BmiEvent.WeightChange -> {
                writeWeightAct(event.newWeightRec)
            }

            is BmiEvent.HeightChange -> {
                writeHeightAct(event.newHeightRec)
            }

            BmiEvent.SaveWeightRecord -> {
                val weightValue = uiState.value.weightValue
                if (weightValue != null) {
                    writeWeightRecordAct(
                        WeightRecordEntity(
                            id = UUID.randomUUID(),
                            dateTime = LocalDateTime.now().toUtc(),
                            weightInKg = convertWeight(
                                weight = Weight(weightValue, uiState.value.weightUnit),
                                toUnit = WeightUnit.Kg
                            ).value,
                        )
                    )
                }
            }

            is BmiEvent.ActivityChange -> {
                writeActivityAct(event.newActivityRec)
            }

            BmiEvent.SaveActivityRecord -> {
                val activity = uiState.value.activity
                if (activity != null) {
                    writeActivityRecordAct(
                        ActivityRecordEntity(
                            id = UUID.randomUUID(),
                            dateTime = LocalDateTime.now().toUtc(),
                            activity = activity
                        )
                    )
                }
            }

            BmiEvent.SaveWaterRecord -> {
                val waterValue = uiState.value.waterValue

                if (waterValue != null) {
                    writeWaterRecordAct(
                        WaterRecordEntity(
                            id = UUID.randomUUID(),
                            dateTime = LocalDateTime.now().toUtc(),
                            water = convertWater(
                                Water(
                                    waterValue,
                                    uiState.value.waterUnit
                                ),
                                toUnit = WaterUnit.L
                            ).value
                        )
                    )
                }
            }

            is BmiEvent.WaterChange -> {
                writeWaterAct(event.newWaterRec)
            }

            is BmiEvent.GlassClick -> {
                val water = uiState.value.waterValue

                if (water != null) {
                    if (event.filled) {
                        // a full glass is clicked so it becomes empty
                        writeWaterAct(Water(water - 0.25, uiState.value.waterUnit))
                    } else {
                        writeWaterAct(Water(water + 0.25, uiState.value.waterUnit))
                    }
                }
            }
        }
    }
}