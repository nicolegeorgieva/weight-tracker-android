package com.weighttracker.domain

import com.weighttracker.domain.data.Height
import com.weighttracker.domain.data.HeightUnit
import com.weighttracker.domain.data.Weight
import com.weighttracker.domain.data.WeightUnit
import java.text.DecimalFormat

fun calculateBmi(
    weight: Weight,
    height: Height
): Double {
    val kg = convertWeight(weight, WeightUnit.Kg).value
    val m = convertHeight(height, HeightUnit.M).value

    return kg / (m * m)
}

fun formatBmi(bmi: Double): String {
    val bmiFormatted = DecimalFormat("###,###.#").format(bmi)
    return bmiFormatted
}