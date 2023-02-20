package com.weighttracker.domain

import java.text.DecimalFormat

fun calculateBmi(
    weight: Double, height: Double,
    kgSelected: Boolean, mSelected: Boolean
): Double {
    val kg = convertToKg(weight, kgSelected)
    val m = convertToM(height, mSelected)

    return kg / (m * m)
}

fun formatBmi(bmi: Double): String {
    val bmiFormatted = DecimalFormat("###,###.#").format(bmi)
    return bmiFormatted
}