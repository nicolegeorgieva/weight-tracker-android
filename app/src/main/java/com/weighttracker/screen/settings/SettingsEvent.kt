package com.weighttracker.screen.settings

import com.weighttracker.domain.data.HeightUnit
import com.weighttracker.domain.data.WaterUnit
import com.weighttracker.domain.data.WeightUnit

sealed interface SettingsEvent {
    data class ChangeWeightUnit(val weightUnit: WeightUnit) : SettingsEvent
    data class ChangeHeightUnit(val heightUnit: HeightUnit) : SettingsEvent
    data class ChangeWaterUnit(val waterUnit: WaterUnit) : SettingsEvent
}