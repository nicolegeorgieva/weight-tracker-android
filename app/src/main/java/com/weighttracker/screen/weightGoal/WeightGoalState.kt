package com.weighttracker.screen.weightGoal

import com.weighttracker.domain.NormalWeightRange

data class WeightGoalState(
    val currentWeight: Double?,
    val weightUnit: String,
    val goalWeight: Double?,
    val weightToLoseOrGain: Double?,
    val idealWeight: Double?,
    val normalWeightRange: NormalWeightRange?,
    val plan: WeightLossPlan?
)

data class WeightLossPlan(
    val optimistic: WeightLossInfo,
    val realistic: WeightLossInfo,
    val pessimistic: WeightLossInfo,
)

data class WeightLossInfo(
    val totalMonths: Double,
    val lossPerMonth: Double
)