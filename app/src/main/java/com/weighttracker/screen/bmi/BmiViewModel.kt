package com.weighttracker.screen.bmi

import com.weighttracker.base.SimpleFlowViewModel
import com.weighttracker.persistence.height.HeightFlow
import com.weighttracker.persistence.height.WriteHeightAct
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
    private val writeHeightAct: WriteHeightAct
) : SimpleFlowViewModel<BmiState, BmiEvent>() {
    override val initialUi = BmiState(
        weight = 0.0, height = 0.0, bmi = 0.0
    )

    override val uiFlow: Flow<BmiState> = combine(
        weightFlow(Unit),
        heightFlow(Unit),
    ) { weight, height ->
        BmiState(
            weight = weight,
            height = height,
            bmi = if (weight != null && height != null) {
                weight / (height * height)
            } else 0.0
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
        }
    }
}