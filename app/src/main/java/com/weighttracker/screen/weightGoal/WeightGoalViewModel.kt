package com.weighttracker.screen.weightGoal

import com.weighttracker.base.SimpleFlowViewModel
import com.weighttracker.domain.NormalWeightRange
import com.weighttracker.domain.calculateNormalWeightRange
import com.weighttracker.domain.data.Height
import com.weighttracker.domain.data.HeightUnit
import com.weighttracker.domain.data.Weight
import com.weighttracker.domain.data.WeightUnit
import com.weighttracker.persistence.datastore.height.HeightFlow
import com.weighttracker.persistence.datastore.kgselected.KgSelectedFlow
import com.weighttracker.persistence.datastore.mselected.MSelectedFlow
import com.weighttracker.persistence.datastore.weight.WeightFlow
import com.weighttracker.persistence.datastore.weightGoal.WeightGoalFlow
import com.weighttracker.persistence.datastore.weightGoal.WriteWeightGoalAct
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import javax.inject.Inject

@HiltViewModel
class WeightGoalViewModel @Inject constructor(
    private val kgSelectedFlow: KgSelectedFlow,
    private val weightFlow: WeightFlow,
    private val weightGoalFlow: WeightGoalFlow,
    private val writeWeightGoalAct: WriteWeightGoalAct,
    private val heightFlow: HeightFlow,
    private val mSelectedFlow: MSelectedFlow
) : SimpleFlowViewModel<WeightGoalState, WeightGoalEvent>() {

    override val initialUi = WeightGoalState(
        currentWeight = 0.0,
        weightUnit = "",
        goalWeight = 0.0,
        weightToLoseOrGain = 0.0,
        idealWeight = 0.0,
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
        ),

        normalWeightRange = NormalWeightRange(
            minWeight = 0.0,
            maxWeight = 0.0
        )
    )

    override val uiFlow: Flow<WeightGoalState> = combine(
        kgSelectedFlow(Unit),
        weightFlow(Unit),
        weightGoalFlow(Unit),
        heightFlow(Unit),
        mSelectedFlow(Unit)
    ) { kgSelected, currentWeight, goalWeight, height, mSelected ->
        val weightToLoseOrGain = if (currentWeight != null && goalWeight != null) {
            currentWeight - goalWeight
        } else 0.0
        WeightGoalState(
            weightUnit = if (kgSelected) "kg" else "lb",
            currentWeight = currentWeight,
            goalWeight = goalWeight,
            weightToLoseOrGain = weightToLoseOrGain,
            idealWeight = if (height != null && currentWeight != null) {
                calculateIdealWeight(
                    height = Height(height, if (mSelected) HeightUnit.M else HeightUnit.Ft),
                    weight = Weight(currentWeight, if (kgSelected) WeightUnit.Kg else WeightUnit.Lb)
                )
            } else {
                null
            },
            plan = weightLossPeriod(weightToLose = weightToLoseOrGain, kgSelected = kgSelected),
            normalWeightRange = if (height != null && currentWeight != null) {
                calculateNormalWeightRange(
                    Height(height, if (mSelected) HeightUnit.M else HeightUnit.Ft),
                    Weight(currentWeight, if (kgSelected) WeightUnit.Kg else WeightUnit.Lb)
                )
            } else {
                null
            }
        )
    }

    private fun calculateIdealWeight(
        height: Height,
        weight: Weight
    ): Double {
        val normalWeight = calculateNormalWeightRange(
            Height(height.value, height.unit),
            Weight(weight.value, weight.unit)
        )

        val minWeight = normalWeight.minWeight
        val maxWeight = normalWeight.maxWeight

        return (maxWeight + minWeight) / 2
    }

    private fun weightLossPeriod(weightToLose: Double, kgSelected: Boolean): WeightLossPlan {
        val optimistic = if (kgSelected) {
            weightToLose / 4 //4 per month
        } else {
            weightToLose / 8.81849049
        }

        val realistic = if (kgSelected) {
            weightToLose / 2
        } else {
            weightToLose / 4.40924524
        }

        val pessimistic = if (kgSelected) {
            weightToLose / 1
        } else {
            weightToLose / 2.20462262
        }

        return WeightLossPlan(
            optimistic = WeightLossInfo(
                totalMonths = optimistic,
                lossPerMonth = if (kgSelected) {
                    4.0
                } else {
                    8.8
                }
            ),
            realistic = WeightLossInfo(
                totalMonths = realistic,
                lossPerMonth = if (kgSelected) {
                    2.0
                } else {
                    4.4
                }
            ),
            pessimistic = WeightLossInfo(
                totalMonths = pessimistic,
                lossPerMonth = if (kgSelected) {
                    1.0
                } else {
                    2.2
                }
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