package com.weighttracker.screen.bmi

sealed interface BmiEvent {
    data class WeightChange(val newWeightRec: Double) : BmiEvent
    data class HeightChange(val newHeightRec: Double) : BmiEvent
    object SaveWeightRecord : BmiEvent
    data class ActivityChange(val newActivityRec: String) : BmiEvent
    object SaveActivityRecord : BmiEvent
    data class WaterChange(val newWaterRec: Double) : BmiEvent
    object SaveWaterRecord : BmiEvent
    data class GlassClick(val filled: Boolean) : BmiEvent
}