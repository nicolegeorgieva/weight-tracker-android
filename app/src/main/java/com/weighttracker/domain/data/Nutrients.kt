package com.weighttracker.domain.data

import com.weighttracker.network.nutrients.MacroNutrient

data class Nutrients(
    val calories: Int,
    val fat: MacroNutrient,
    val protein: MacroNutrient,
    val carbs: MacroNutrient,
    val fiber: MacroNutrient,
    val totalWeight: Int
)