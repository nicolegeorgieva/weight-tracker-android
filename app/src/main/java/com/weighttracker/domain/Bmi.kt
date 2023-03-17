package com.weighttracker.domain

import com.weighttracker.domain.data.Weight
import com.weighttracker.domain.data.WeightUnit
import java.text.DecimalFormat

fun calculateBmi(
    weight: Weight,
    height: Double,
    mSelected: Boolean
): Double {
    val kg = convertWeight(weight, WeightUnit.Kg)
    val m = convertToM(height, mSelected)

    return kg.value / (m * m)
}

fun formatBmi(bmi: Double): String {
    val bmiFormatted = DecimalFormat("###,###.#").format(bmi)
    return bmiFormatted
}