package com.weighttracker.domain.network

import com.weighttracker.domain.data.Exercise
import com.weighttracker.network.exercise.ExerciseResponse

fun mapExerciseResponse(response: List<ExerciseResponse>): List<Exercise> {
    return response.mapNotNull { exerciseResponse ->
        val name = exerciseResponse.name?.takeIf { it.isNotBlank() }
            ?: return@mapNotNull null
        Exercise(
            name = name,
            youtubeLink = exerciseResponse.youtubeLink?.takeIf { it.isNotBlank() },
            primaryMuscles = exerciseResponse.primaryMuscles
                ?.filter { it.isNotBlank() }
                ?.takeIf { it.isNotEmpty() },
            secondaryMuscles = exerciseResponse.secondaryMuscles
                ?.filter { it.isNotBlank() }
                ?.takeIf { it.isNotEmpty() },
        )
    }
}