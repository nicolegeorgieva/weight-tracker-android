package com.weighttracker.screen.bmi

import com.weighttracker.base.SimpleFlowViewModel
import com.weighttracker.persistence.height.HeightFlow
import com.weighttracker.persistence.height.WriteHeightAct
import com.weighttracker.persistence.kgselected.KgSelectedFlow
import com.weighttracker.persistence.mselected.MSelectedFlow
import com.weighttracker.persistence.quote.QuoteFlow
import com.weighttracker.persistence.weight.WeightFlow
import com.weighttracker.persistence.weight.WriteWeightAct
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import javax.inject.Inject

@HiltViewModel
class BmiViewModel @Inject constructor(
    private val weightFlow: WeightFlow,
    private val writeWeightAct: WriteWeightAct,
    private val heightFlow: HeightFlow,
    private val writeHeightAct: WriteHeightAct,
    private val kgSelectedFlow: KgSelectedFlow,
    private val mSelectedFlow: MSelectedFlow,
    private val quoteFlow: QuoteFlow
) : SimpleFlowViewModel<BmiState, BmiEvent>() {
    override val initialUi = BmiState(
        weight = 0.0,
        height = 0.0,
        bmi = 0.0,
        kg = true,
        m = true,
        quote = "",
        normalWeightRange = null
    )

    override val uiFlow: Flow<BmiState> = combine(
        weightFlow(Unit),
        heightFlow(Unit),
        kgSelectedFlow(Unit),
        mSelectedFlow(Unit),
        quoteFlow(Unit)
    ) { weight, height, kg, m, quote ->
        BmiState(
            weight = weight,
            height = height,
            bmi = if (weight != null && height != null) {
                calculateBmi(weight, height, kg, m)
            } else 0.0,
            kg = kg,
            m = m,
            quote = quote,
            normalWeightRange = height?.let { calculateNormalWeightRange(it, m) }
        )
    }

    private fun calculateNormalWeightRange(height: Double, mSelected: Boolean):
            Pair<Double, Double> {
        val heightInM = convertToM(height, mSelected)
        val minWeight = 18.5 * (heightInM * heightInM)
        val maxWeight = 24.9 * (heightInM * heightInM)

        return Pair(minWeight, maxWeight)
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

    private fun convertToM(height: Double, mSelected: Boolean): Double {
        return if (mSelected) {
            height
        } else {
            // foot selected
            height * 0.3048
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
        }
    }
}