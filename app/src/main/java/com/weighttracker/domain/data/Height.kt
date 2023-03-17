package com.weighttracker.domain.data

enum class HeightUnit {
    M, Ft
}

data class Height(
    val value: Double,
    val unit: HeightUnit
)