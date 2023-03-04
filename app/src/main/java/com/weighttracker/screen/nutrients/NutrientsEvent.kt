package com.weighttracker.screen.nutrients

sealed interface NutrientsEvent {
    data class QuantityChange(val quantityChange: Int?) : NutrientsEvent
    data class SelectFood(val food: String?) : NutrientsEvent
    data class SelectSize(val size: FoodSize) : NutrientsEvent
    object RetryNutrientsRequest : NutrientsEvent
}