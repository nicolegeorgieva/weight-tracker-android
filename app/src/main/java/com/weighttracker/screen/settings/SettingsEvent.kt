package com.weighttracker.screen.settings

import com.weighttracker.domain.data.WeightUnit

sealed interface SettingsEvent {
    data class ChangeWeightUnit(val weightUnit: WeightUnit) : SettingsEvent
    data class MSelect(val m: Boolean) : SettingsEvent
    data class LSelect(val l: Boolean) : SettingsEvent
}