package com.weighttracker.screen.settings

import com.weighttracker.domain.data.HeightUnit
import com.weighttracker.domain.data.WaterUnit
import com.weighttracker.domain.data.WeightUnit

data class SettingsState(
    val weightUnit: WeightUnit,
    val heightUnit: HeightUnit,
    val waterUnit: WaterUnit
)