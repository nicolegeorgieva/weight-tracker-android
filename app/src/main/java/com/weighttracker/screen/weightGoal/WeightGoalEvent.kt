package com.weighttracker.screen.weightGoal

sealed interface WeightGoalEvent {
    data class WeightGoalInput(val targetWeight: Double?) : WeightGoalEvent
}