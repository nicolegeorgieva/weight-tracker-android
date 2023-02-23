package com.weighttracker.domain

data class NormalWeightRange(
    val minWeight: Double,
    val maxWeight: Double
)

fun calculateNormalWeightRange(
    height: Double,
    mSelected: Boolean,
    kgSelected: Boolean
): NormalWeightRange {
    val heightInM = convertToM(height, mSelected)
    val minWeightInKg = 18.5 * (heightInM * heightInM)
    val maxWeightInKg = 24.9 * (heightInM * heightInM)

    return if (kgSelected) {
        NormalWeightRange(minWeightInKg, maxWeightInKg)
    } else {
        val minWeightInLb = minWeightInKg * 2.205
        val maxWeightInLb = maxWeightInKg * 2.205
        NormalWeightRange(minWeightInLb, maxWeightInLb)
    }
}