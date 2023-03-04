package com.weighttracker.screen.nutrients

import com.weighttracker.network.NetworkError
import com.weighttracker.network.RemoteCall
import com.weighttracker.network.calories.NutrientsResponse

data class NutrientsState(
    val quantity: Int?,
    val foods: List<String>,
    val selectedFood: String?,
    val selectedSize: FoodSize,
    val nutrientsRequest: RemoteCall<NetworkError, NutrientsResponse>?
)

enum class FoodSize {
    Small, Medium, Large
}