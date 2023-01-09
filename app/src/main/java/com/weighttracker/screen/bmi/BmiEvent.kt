package com.weighttracker.screen.bmi

sealed interface BmiEvent {
    data class WeightChange(val newWeightRec: Double) : BmiEvent
    data class HeightChange(val newHeightRec: Double) : BmiEvent
    object SaveWeightRecord : BmiEvent
}