package com.weighttracker.domain.data

enum class WaterUnit {
    L, Gal
}

data class Water(
    val value: Double,
    val unit: WaterUnit
)