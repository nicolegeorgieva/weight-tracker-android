package com.weighttracker.screen.bmi

import com.weighttracker.domain.NormalWeightRange
import com.weighttracker.domain.data.HeightUnit
import com.weighttracker.domain.data.WaterUnit
import com.weighttracker.domain.data.WeightUnit

data class BmiState(
    val weightValue: Double?,
    val weightUnit: WeightUnit,
    val heightValue: Double?,
    val heightUnit: HeightUnit,
    val bmi: Double?,
    val quote: String?,
    val normalWeightRange: NormalWeightRange?,
    val activity: String?,
    val waterValue: Double?,
    val waterUnit: WaterUnit,
    val glasses: List<Boolean>
)