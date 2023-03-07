package com.weighttracker.domain.network

import com.weighttracker.domain.data.Nutrients
import com.weighttracker.network.nutrients.NutrientsResponse

fun mapNutrientsResponse(response: NutrientsResponse): Nutrients {
    return Nutrients(
        calories = response.calories,
        fat = response.nutrients.fat,
        protein = response.nutrients.protein,
        carbs = response.nutrients.carbs,
        fiber = response.nutrients.fiber,
        totalWeight = response.totalWeight
    )
}