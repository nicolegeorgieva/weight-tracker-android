package com.weighttracker.screen.settings

sealed interface SettingsEvent {
    data class WeightChange(val newWeightRec: Double) : SettingsEvent
    data class HeightChange(val newHeightRec: Double) : SettingsEvent
}