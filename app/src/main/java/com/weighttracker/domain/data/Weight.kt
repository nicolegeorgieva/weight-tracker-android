package com.weighttracker.domain.data

enum class WeightUnit {
    Kg, Lb
}

data class Weight(
    val value: Double,
    val unit: WeightUnit
)