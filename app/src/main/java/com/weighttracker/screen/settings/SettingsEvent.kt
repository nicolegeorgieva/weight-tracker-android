package com.weighttracker.screen.settings

sealed interface SettingsEvent {
    data class KgSelect(val kg: Boolean) : SettingsEvent
    data class MSelect(val m: Boolean) : SettingsEvent
    data class LSelect(val l: Boolean) : SettingsEvent
}