package com.weighttracker.domain

//We have 4 glasses at the beginning.
//After last is full new 4 glasses appear.
//After last is full new 4 glasses appear.
//Each full glass becomes true.

fun glasses(water: Double): List<Boolean> {
    val list = mutableListOf<Boolean>()
    val fullGlasses = (water / 0.25).toInt()

    for (i in 1..fullGlasses) {
        list.add(true)
    }

    val totalGlasses = when (fullGlasses) {
        in 0..3 -> 4
        in 4..7 -> 8
        else -> 12
    }

    for (i in 1..totalGlasses - fullGlasses) {
        list.add(false)
    }

    return list
}