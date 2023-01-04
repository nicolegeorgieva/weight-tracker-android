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
        plan = WeightLossPlan(
            optimistic = WeightLossInfo(
                totalMonths = 0.0,
                lossPerMonth = 0.0
            ),
            realistic = WeightLossInfo(
                totalMonths = 0.0,
                lossPerMonth = 0.0
            ),
            pessimistic = WeightLossInfo(
                totalMonths = 0.0,
                lossPerMonth = 0.0
            )
        )
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
            plan = weightLossPeriod(weightToLose = weightToLose)
        )
    }

    private fun weightLossPeriod(weightToLose: Double): WeightLossPlan {
        val optimistic = weightToLose / 4 //4 per month
        val realistic = weightToLose / 2
        val pessimistic = weightToLose / 1

        return WeightLossPlan(
            optimistic = WeightLossInfo(
                totalMonths = optimistic,
                lossPerMonth = 4.0
            ),
            realistic = WeightLossInfo(
                totalMonths = realistic,
                lossPerMonth = 2.0
            ),
            pessimistic = WeightLossInfo(
                totalMonths = pessimistic,
                lossPerMonth = 1.0
            )
        )
    }

    override suspend fun handleEvent(event: WeightGoalEvent) {
        when (event) {
            is WeightGoalEvent.WeightGoalInput -> {
                event.targetWeight?.let { writeWeightGoalAct(it) }
            }
        }
    }
}