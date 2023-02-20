package com.weighttracker.domain

fun glasses(water: Double): List<Boolean> {
    return when (water) {
        in 0.0..0.24 -> listOf(false, false, false, false)
        in 0.25..0.49 -> listOf(true, false, false, false)
        in 0.5..0.749 -> listOf(true, true, false, false)
        in 0.75..0.99 -> listOf(true, true, true, false)
        in 1.0..1.24 -> listOf(
            true, true, true, true,
            false, false, false, false
        )
        in 1.25..1.49 -> listOf(
            true, true, true, true, true,
            false, false, false
        )
        in 1.5..1.749 -> listOf(
            true, true, true, true,
            true, true, false, false
        )
        in 1.75..1.99 -> listOf(
            true, true, true, true,
            true, true, true, false
        )
        in 2.0..2.24 -> listOf(
            true, true, true, true,
            true, true, true, true,
            false, false, false, false
        )
        in 2.25..2.49 -> listOf(
            true, true, true, true,
            true, true, true, true,
            true, false, false, false
        )
        in 2.5..2.749 -> listOf(
            true, true, true, true,
            true, true, true, true,
            true, true, false, false
        )
        in 2.75..2.9 -> listOf(
            true, true, true, true,
            true, true, true, true,
            true, true, true, false
        )
        in 3.0..3.9 -> listOf(
            true, true, true, true,
            true, true, true, true,
            true, true, true, true
        )
        else -> {
            emptyList()
        }
    }
}