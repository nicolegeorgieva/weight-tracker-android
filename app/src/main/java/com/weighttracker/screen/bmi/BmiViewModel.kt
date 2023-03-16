package com.weighttracker.screen.bmi

import com.weighttracker.base.SimpleFlowViewModel
import com.weighttracker.combine
import com.weighttracker.domain.calculateBmi
import com.weighttracker.domain.calculateNormalWeightRange
import com.weighttracker.domain.convert
import com.weighttracker.domain.data.Weight
import com.weighttracker.domain.data.WeightUnit
import com.weighttracker.domain.glasses
import com.weighttracker.persistence.database.activityrecords.ActivityRecordEntity
import com.weighttracker.persistence.database.activityrecords.WriteActivityRecordAct
import com.weighttracker.persistence.database.waterrecords.WaterRecordEntity
import com.weighttracker.persistence.database.waterrecords.WriteWaterRecordAct
import com.weighttracker.persistence.database.weightrecords.WeightRecordEntity
import com.weighttracker.persistence.database.weightrecords.WriteWeightRecordAct
import com.weighttracker.persistence.datastore.activity.ActivityFlow
import com.weighttracker.persistence.datastore.activity.WriteActivityAct
import com.weighttracker.persistence.datastore.height.HeightFlow
import com.weighttracker.persistence.datastore.height.WriteHeightAct
import com.weighttracker.persistence.datastore.kgselected.KgSelectedFlow
import com.weighttracker.persistence.datastore.lselected.LSelectedFlow
import com.weighttracker.persistence.datastore.mselected.MSelectedFlow
import com.weighttracker.persistence.datastore.quote.QuoteFlow
import com.weighttracker.persistence.datastore.water.WaterFlow
import com.weighttracker.persistence.datastore.water.WriteWaterAct
import com.weighttracker.persistence.datastore.weight.WeightFlow
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
    private val writeWeightAct: WriteWeightAct,
    private val heightFlow: HeightFlow,
    private val writeHeightAct: WriteHeightAct,
    private val kgSelectedFlow: KgSelectedFlow,
    private val mSelectedFlow: MSelectedFlow,
    private val quoteFlow: QuoteFlow,
    private val writeWeightRecordAct: WriteWeightRecordAct,
    private val activityFlow: ActivityFlow,
    private val writeActivityAct: WriteActivityAct,
    private val writeActivityRecordAct: WriteActivityRecordAct,
    private val waterFlow: WaterFlow,
    private val writeWaterAct: WriteWaterAct,
    private val writeWaterRecordAct: WriteWaterRecordAct,
    private val lSelectedFlow: LSelectedFlow
) : SimpleFlowViewModel<BmiState, BmiEvent>() {
    override val initialUi = BmiState(
        weight = 0.0,
        height = 0.0,
        bmi = 0.0,
        kg = true,
        m = true,
        quote = "",
        normalWeightRange = null,
        activity = "",
        water = 0.0,
        l = true,
        glasses = emptyList()
    )

    override val uiFlow: Flow<BmiState> = combine(
        weightFlow(Unit),
        heightFlow(Unit),
        kgSelectedFlow(Unit),
        mSelectedFlow(Unit),
        quoteFlow(Unit),
        activityFlow(Unit),
        waterFlow(Unit),
        lSelectedFlow(Unit)
    ) { weight, height, kgSelected, mSelected, quote, activity, water, lSelected ->
        BmiState(
            weight = weight,
            height = height,
            bmi = if (weight != null && height != null && weight > 0 && height > 0) {
                calculateBmi(
                    Weight(weight, if (kgSelected) WeightUnit.Kg else WeightUnit.Lb),
                    height,
                    mSelected
                )
            } else null,
            kg = kgSelected,
            m = mSelected,
            quote = quote,
            activity = activity,
            water = water ?: 0.0,
            normalWeightRange = if (height != null) {
                calculateNormalWeightRange(height, mSelected, kgSelected)
            } else {
                null
            },
            l = lSelected,
            glasses = glasses(water ?: 0.0)
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
                val weight = uiState.value.weight
                if (weight != null) {
                    writeWeightRecordAct(
                        WeightRecordEntity(
                            id = UUID.randomUUID(),
                            dateTime = LocalDateTime.now().toUtc(),
                            weightInKg = convert(
                                weight = Weight(
                                    weight,
                                    if (uiState.value.kg) WeightUnit.Kg else WeightUnit.Lb
                                ),
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
                val water = uiState.value.water
                if (water != null) {
                    writeWaterRecordAct(
                        WaterRecordEntity(
                            id = UUID.randomUUID(),
                            dateTime = LocalDateTime.now().toUtc(),
                            water = water
                        )
                    )
                }
            }
            is BmiEvent.WaterChange -> {
                writeWaterAct(event.newWaterRec)
            }
            is BmiEvent.GlassClick -> {
                val water = uiState.value.water
                if (event.filled) { //a full glass is clicked so it becomes empty
                    writeWaterAct(water - 0.25)
                } else {
                    writeWaterAct(water + 0.25)
                }
            }
        }
    }
}