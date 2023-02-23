package com.weighttracker.screen.bmi

import com.weighttracker.domain.NormalWeightRange

data class BmiState(
    val weight: Double?,
    val height: Double?,
    val bmi: Double?,
    val kg: Boolean,
    val m: Boolean,
    val quote: String?,
    val normalWeightRange: NormalWeightRange?,
    val activity: String?,
    val water: Double,
    val glasses: List<Boolean>
)