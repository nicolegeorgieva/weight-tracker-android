package com.weighttracker.domain

import com.weighttracker.domain.data.Water
import com.weighttracker.domain.data.WaterUnit

//We have 4 glasses at the beginning.
//After last is full new 4 glasses appear.
//After last is full new 4 glasses appear.
//Each full glass becomes true.

fun glasses(water: Water): List<Boolean> {
    val list = mutableListOf<Boolean>()

    val fullGlasses = if (water.unit == WaterUnit.L) (water.value / 0.25).toInt()
    else (water.value / 0.0660430131).toInt()

    for (i in 1..fullGlasses) {
        list.add(true)
    }

    val totalGlasses = when (fullGlasses) {
        in 0..3 -> 4
        in 4..7 -> 8
        else -> 12
    }

    val emptyGlasses = totalGlasses - fullGlasses

    for (i in 1..emptyGlasses) {
        list.add(false)
    }

    return list
}