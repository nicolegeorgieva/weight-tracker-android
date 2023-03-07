package com.weighttracker.screen.exercise

import com.weighttracker.domain.data.Exercise
import com.weighttracker.network.NetworkError
import com.weighttracker.network.RemoteCall

data class ExerciseState(
    val muscle: List<String>,
    val selectedMuscle: String?,
    val exerciseRequest: RemoteCall<NetworkError, List<Exercise>>?
)