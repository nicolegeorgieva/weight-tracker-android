package com.weighttracker.domain

fun convertToKg(weight: Double, kgSelected: Boolean): Double {
    return if (kgSelected) {
        weight
    } else {
        // lb selected
        weight * 0.45359237
    }
}

fun convertToM(height: Double, mSelected: Boolean): Double {
    return if (mSelected) {
        height
    } else {
        // foot selected
        height * 0.3048
    }
}