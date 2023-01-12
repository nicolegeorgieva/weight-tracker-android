package com.weighttracker.screen.bmi

import com.weighttracker.base.SimpleFlowViewModel
import com.weighttracker.combine
import com.weighttracker.domain.calculateNormalWeightRange
import com.weighttracker.domain.convertToM
import com.weighttracker.persistence.database.activityrecords.ActivityRecordEntity
import com.weighttracker.persistence.database.activityrecords.WriteActivityRecordAct
import com.weighttracker.persistence.database.weightrecords.WeightRecordEntity
import com.weighttracker.persistence.database.weightrecords.WriteWeightRecordAct
import com.weighttracker.persistence.datastore.activity.ActivityFlow
import com.weighttracker.persistence.datastore.activity.WriteActivityAct
import com.weighttracker.persistence.datastore.height.HeightFlow
import com.weighttracker.persistence.datastore.height.WriteHeightAct
import com.weighttracker.persistence.datastore.kgselected.KgSelectedFlow
import com.weighttracker.persistence.datastore.mselected.MSelectedFlow
import com.weighttracker.persistence.datastore.quote.QuoteFlow
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
    private val writeActivityRecordAct: WriteActivityRecordAct
) : SimpleFlowViewModel<BmiState, BmiEvent>() {
    override val initialUi = BmiState(
        weight = 0.0,
        height = 0.0,
        bmi = 0.0,
        kg = true,
        m = true,
        quote = "",
        normalWeightRange = null,
        activity = ""
    )

    override val uiFlow: Flow<BmiState> = combine(
        weightFlow(Unit),
        heightFlow(Unit),
        kgSelectedFlow(Unit),
        mSelectedFlow(Unit),
        quoteFlow(Unit),
        activityFlow(Unit),
    ) { weight, height, kgSelected, mSelected, quote, activity ->
        BmiState(
            weight = weight,
            height = height,
            bmi = if (weight != null && height != null && weight > 0 && height > 0) {
                calculateBmi(weight, height, kgSelected, mSelected)
            } else null,
            kg = kgSelected,
            m = mSelected,
            quote = quote,
            activity = activity,
            normalWeightRange = if (height != null) {
                calculateNormalWeightRange(height, mSelected, kgSelected)
            } else {
                null
            }
        )
    }

    private fun calculateBmi(
        weight: Double, height: Double,
        kgSelected: Boolean, mSelected: Boolean
    ): Double {
        val kg = convertToKg(weight, kgSelected)
        val m = convertToM(height, mSelected)

        return kg / (m * m)
    }

    private fun convertToKg(weight: Double, kgSelected: Boolean): Double {
        return if (kgSelected) {
            weight
        } else {
            // lb selected
            weight * 0.45359237
        }
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
                            weightKg = weight
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
        }
    }
}