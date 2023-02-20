package com.weighttracker.domain

fun calculateNormalWeightRange(
    height: Double,
    mSelected: Boolean,
    kgSelected: Boolean
): Pair<Double, Double> {
    val heightInM = convertToM(height, mSelected)
    val minWeightInKg = 18.5 * (heightInM * heightInM)
    val maxWeightInKg = 24.9 * (heightInM * heightInM)

    return if (kgSelected) {
        Pair(minWeightInKg, maxWeightInKg)
    } else {
        val minWeightInLb = minWeightInKg * 2.205
        val maxWeightInLb = maxWeightInKg * 2.205
        Pair(minWeightInLb, maxWeightInLb)
    }
}