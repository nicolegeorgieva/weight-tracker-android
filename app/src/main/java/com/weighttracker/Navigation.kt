package com.weighttracker

import androidx.compose.runtime.mutableStateOf

val currentScreen = mutableStateOf<Screens>(Screens.BMI)

sealed interface Screens {
    object BMI : Screens
    object Settings : Screens
    object Converter : Screens
    data class Quote(val backTo: Screens) : Screens
    object WeightGoal : Screens
    object Articles : Screens
    data class WeightRecords(val backTo: Screens) : Screens
    data class ActivityRecords(val backTo: Screens) : Screens
    data class WaterRecords(val backTo: Screens) : Screens
    object Nutrients : Screens
}

fun navigateTo(screens: Screens) {
    currentScreen.value = screens
}