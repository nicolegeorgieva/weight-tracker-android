package com.weighttracker.domain

import com.weighttracker.domain.data.Height
import com.weighttracker.domain.data.HeightUnit
import com.weighttracker.domain.data.Weight
import com.weighttracker.domain.data.WeightUnit

data class NormalWeightRange(
    val minWeight: Double,
    val maxWeight: Double
)

fun calculateNormalWeightRange(
    height: Height,
    weight: Weight
): NormalWeightRange {
    val heightInM = convertHeight(height, HeightUnit.M).value
    val minWeightInKg = 18.5 * (heightInM * heightInM)
    val maxWeightInKg = 25 * (heightInM * heightInM)

    return if (weight.unit == WeightUnit.Kg) {
        NormalWeightRange(minWeightInKg, maxWeightInKg)
    } else {
        val minWeightInLb = minWeightInKg * 2.2046
        val maxWeightInLb = maxWeightInKg * 2.2046

        NormalWeightRange(minWeightInLb, maxWeightInLb)
    }
}