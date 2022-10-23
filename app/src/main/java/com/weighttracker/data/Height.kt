package com.weighttracker.data

enum class HeightUnit {
    Meters,
    Feet
}

data class Height(
    val value: Double,
    val unit: HeightUnit
)