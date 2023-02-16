package com.weighttracker

import androidx.compose.runtime.mutableStateOf

val currentScreen = mutableStateOf<Screens>(Screens.BMI)

sealed interface Screens {
    object BMI : Screens
    object Settings : Screens
    object Converter : Screens
    object Quote : Screens
    object WeightGoal : Screens
    object Articles : Screens
    data class WeightRecords(val backTo: Screens) : Screens
    object ActivityRecords : Screens
    object WaterRecords : Screens
}

fun navigateTo(screens: Screens) {
    currentScreen.value = screens
}