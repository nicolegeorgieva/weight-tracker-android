package com.weighttracker.screen.bmi

data class BmiState(
    val weight: Double?,
    val height: Double?,
    val bmi: Double?,
    val kg: Boolean,
    val m: Boolean
)