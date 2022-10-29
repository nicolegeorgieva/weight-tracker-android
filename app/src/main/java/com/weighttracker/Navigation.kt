package com.weighttracker

import androidx.compose.runtime.mutableStateOf

val currentScreen = mutableStateOf(Screens.Main)

enum class Screens {
    Main, Converter, Hello, About, BMI
}

fun navigateTo(screens: Screens) {
    currentScreen.value = screens
}