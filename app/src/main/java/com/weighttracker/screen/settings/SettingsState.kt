package com.weighttracker.screen.settings

import com.weighttracker.domain.data.WeightUnit

data class SettingsState(
    val weightUnit: WeightUnit,
    val m: Boolean,
    val l: Boolean
)