package com.weighttracker.screen.weightGoal

data class WeightGoalState(
    val currentWeight: Double?,
    val weightUnit: String,
    val goalWeight: Double?,
    val weightToLose: Double?,
    val weightLossPeriod: WeightLossPeriod?
)

data class WeightLossPeriod(
    val optimisticMonths: Double,
    val realisticMonths: Double,
    val pessimisticMonths: Double
)