package com.weighttracker.domain.data

data class Exercise(
    val name: String,
    val youtubeLink: String?,
    val primaryMuscles: List<String>?,
    val secondaryMuscles: List<String>?,
)