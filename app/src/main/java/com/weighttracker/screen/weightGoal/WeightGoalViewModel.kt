package com.weighttracker.screen.weightGoal

import com.weighttracker.base.SimpleFlowViewModel
import com.weighttracker.domain.NormalWeightRange
import com.weighttracker.domain.calculateNormalWeightRange
import com.weighttracker.domain.data.Height
import com.weighttracker.domain.data.HeightUnit
import com.weighttracker.domain.data.Weight
import com.weighttracker.domain.data.WeightUnit
import com.weighttracker.persistence.datastore.height.HeightFlow
import com.weighttracker.persistence.datastore.mselected.MSelectedFlow
import com.weighttracker.persistence.datastore.weight.WeightFlow
import com.weighttracker.persistence.datastore.weight.WeightUnitFlow
import com.weighttracker.persistence.datastore.weightGoal.WeightGoalFlow
import com.weighttracker.persistence.datastore.weightGoal.WriteWeightGoalAct
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import javax.inject.Inject

@HiltViewModel
class WeightGoalViewModel @Inject constructor(
    private val weightFlow: WeightFlow,
    private val weightUnitFlow: WeightUnitFlow,
    private val weightGoalFlow: WeightGoalFlow,
    private val writeWeightGoalAct: WriteWeightGoalAct,
    private val heightFlow: HeightFlow,
    private val mSelectedFlow: MSelectedFlow
) : SimpleFlowViewModel<WeightGoalState, WeightGoalEvent>() {

    override val initialUi = WeightGoalState(
        currentWeight = 0.0,
        weightUnit = "kg",
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
        weightFlow(Unit),
        weightUnitFlow(Unit),
        weightGoalFlow(Unit),
        heightFlow(Unit),
        mSelectedFlow(Unit)
    ) { currentWeight, weightUnit, goalWeight, height, mSelected ->
        val weightToLoseOrGain = if (currentWeight != null && goalWeight != null) {
            currentWeight.value - goalWeight
        } else 0.0
        WeightGoalState(
            weightUnit = when (weightUnit) {
                WeightUnit.Kg -> "kg"
                WeightUnit.Lb -> "lb"
            },
            currentWeight = currentWeight?.value,
            goalWeight = goalWeight,
            weightToLoseOrGain = weightToLoseOrGain,
            idealWeight = if (height != null && currentWeight != null) {
                calculateIdealWeight(
                    height = Height(height, if (mSelected) HeightUnit.M else HeightUnit.Ft),
                    weight = currentWeight
                )
            } else {
                null
            },
            plan = weightLossPeriod(weightToLose = weightToLoseOrGain, weightUnit = weightUnit),
            normalWeightRange = if (height != null && currentWeight != null) {
                calculateNormalWeightRange(
                    Height(height, if (mSelected) HeightUnit.M else HeightUnit.Ft),
                    currentWeight
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

    private fun weightLossPeriod(weightToLose: Double, weightUnit: WeightUnit): WeightLossPlan {
        val optimistic = if (weightUnit == WeightUnit.Kg) {
            weightToLose / 4 //4 per month
        } else {
            weightToLose / 8.81849049
        }

        val realistic = if (weightUnit == WeightUnit.Kg) {
            weightToLose / 2
        } else {
            weightToLose / 4.40924524
        }

        val pessimistic = if (weightUnit == WeightUnit.Kg) {
            weightToLose / 1
        } else {
            weightToLose / 2.20462262
        }

        return WeightLossPlan(
            optimistic = WeightLossInfo(
                totalMonths = optimistic,
                lossPerMonth = if (weightUnit == WeightUnit.Kg) {
                    4.0
                } else {
                    8.8
                }
            ),
            realistic = WeightLossInfo(
                totalMonths = realistic,
                lossPerMonth = if (weightUnit == WeightUnit.Kg) {
                    2.0
                } else {
                    4.4
                }
            ),
            pessimistic = WeightLossInfo(
                totalMonths = pessimistic,
                lossPerMonth = if (weightUnit == WeightUnit.Kg) {
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