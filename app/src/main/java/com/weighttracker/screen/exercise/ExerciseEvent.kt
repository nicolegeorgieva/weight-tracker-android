package com.weighttracker.screen.exercise

sealed interface ExerciseEvent {
    data class SelectMuscle(val muscle: String?) : ExerciseEvent
    object RetryExerciseRequest : ExerciseEvent
}