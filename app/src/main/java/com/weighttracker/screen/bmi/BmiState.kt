package com.weighttracker.screen.bmi

import com.weighttracker.domain.NormalWeightRange
import com.weighttracker.domain.data.WeightUnit

data class BmiState(
    val weightValue: Double?,
    val weightUnit: WeightUnit,
    val height: Double?,
    val bmi: Double?,
    val m: Boolean,
    val quote: String?,
    val normalWeightRange: NormalWeightRange?,
    val activity: String?,
    val water: Double,
    val l: Boolean,
    val glasses: List<Boolean>
)