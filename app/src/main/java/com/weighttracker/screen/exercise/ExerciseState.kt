package com.weighttracker.screen.exercise

import com.weighttracker.network.NetworkError
import com.weighttracker.network.RemoteCall
import com.weighttracker.network.exercise.ExerciseResponse

data class ExerciseState(
    val muscle: List<String>,
    val selectedMuscle: String?,
    val exerciseRequest: RemoteCall<NetworkError, List<ExerciseResponse>>?
)