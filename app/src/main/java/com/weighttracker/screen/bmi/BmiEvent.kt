package com.weighttracker.screen.bmi

import com.weighttracker.domain.data.Height
import com.weighttracker.domain.data.Water
import com.weighttracker.domain.data.Weight

sealed interface BmiEvent {
    data class WeightChange(val newWeightRec: Weight) : BmiEvent
    data class HeightChange(val newHeightRec: Height) : BmiEvent
    object SaveWeightRecord : BmiEvent
    data class ActivityChange(val newActivityRec: String) : BmiEvent
    object SaveActivityRecord : BmiEvent
    data class WaterChange(val newWaterRec: Water) : BmiEvent
    object SaveWaterRecord : BmiEvent
    data class GlassClick(val filled: Boolean) : BmiEvent
}