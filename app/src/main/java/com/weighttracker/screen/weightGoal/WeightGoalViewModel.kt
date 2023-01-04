package com.weighttracker.screen.weightGoal

import com.weighttracker.base.SimpleFlowViewModel
import com.weighttracker.persistence.kgselected.KgSelectedFlow
import com.weighttracker.persistence.weight.WeightFlow
import com.weighttracker.persistence.weightGoal.WeightGoalFlow
import com.weighttracker.persistence.weightGoal.WriteWeightGoalAct
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import javax.inject.Inject

@HiltViewModel
class WeightGoalViewModel @Inject constructor(
    private val kgSelectedFlow: KgSelectedFlow,
    private val weightFlow: WeightFlow,
    private val weightGoalFlow: WeightGoalFlow,
    private val writeWeightGoalAct: WriteWeightGoalAct
) : SimpleFlowViewModel<WeightGoalState, WeightGoalEvent>() {
    override val initialUi = WeightGoalState(
        currentWeight = 0.0, weightUnit = "", goalWeight = 0.0, weightToLose = 0.0,
        weightLossPeriod = WeightLossPeriod(0.0, 0.0, 0.0)
    )

    override val uiFlow: Flow<WeightGoalState> = combine(
        kgSelectedFlow(Unit),
        weightFlow(Unit),
        weightGoalFlow(Unit)
    ) { kgSelected, currentWeight, goalWeight ->
        val weightToLose = if (currentWeight != null && goalWeight != null) {
            currentWeight - goalWeight
        } else 0.0
        WeightGoalState(
            weightUnit = if (kgSelected) "kg" else "lb",
            currentWeight = currentWeight,
            goalWeight = goalWeight,
            weightToLose = weightToLose,
            weightLossPeriod = weightLossPeriod(weightToLose = weightToLose)
        )
    }

    private fun weightLossPeriod(weightToLose: Double): WeightLossPeriod {
        val optimisticPeriod = weightToLose / 4 //4 per month
        val realisticPeriod = weightToLose / 2
        val pessimisticPeriod = weightToLose / 1

        return WeightLossPeriod(optimisticPeriod, realisticPeriod, pessimisticPeriod)
    }

    override suspend fun handleEvent(event: WeightGoalEvent) {
        when (event) {
            is WeightGoalEvent.WeightGoalInput -> {
                event.targetWeight?.let { writeWeightGoalAct(it) }
            }
        }
    }
}