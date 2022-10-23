package com.weighttracker.data

enum class WeightUnit {
    Kgs,
    Pounds
}

data class Weight(
    val value: Double,
    val unit: WeightUnit
)