package com.weighttracker.screen.nutrients

import com.weighttracker.domain.data.Nutrients
import com.weighttracker.network.NetworkError
import com.weighttracker.network.RemoteCall

data class NutrientsState(
    val quantity: Int?,
    val foods: List<String>,
    val selectedFood: String?,
    val selectedSize: FoodSize,
    val nutrientsRequest: RemoteCall<NetworkError, Nutrients>?
)

enum class FoodSize {
    Small, Medium, Large
}