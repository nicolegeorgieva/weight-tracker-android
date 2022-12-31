package com.weighttracker

import androidx.compose.runtime.mutableStateOf

val currentScreen = mutableStateOf(Screens.BMI)

enum class Screens {
    BMI, Settings
}

fun navigateTo(screens: Screens) {
    currentScreen.value = screens
}