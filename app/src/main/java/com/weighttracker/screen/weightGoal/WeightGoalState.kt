package com.weighttracker.screen.weightGoal

data class WeightGoalState(
    val currentWeight: Double?,
    val weightUnit: String,
    val goalWeight: Double?,
    val weightToLose: Double?,
    val idealWeight: Double?,
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