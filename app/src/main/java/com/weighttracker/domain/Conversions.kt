package com.weighttracker.domain

import com.weighttracker.domain.data.Weight
import com.weighttracker.domain.data.WeightUnit

fun convert(weightInKg: Double, unit: WeightUnit): Double {
    return when (unit) {
        WeightUnit.Kg -> weightInKg
        WeightUnit.Lb -> weightInKg * 2.2046
    }
}

//"50 kg", to unit "lb" : "100 lb"
fun convert(weight: Weight, toUnit: WeightUnit): Weight {
    return when (weight.unit) {
        WeightUnit.Kg -> if (toUnit == WeightUnit.Kg) {
            Weight(weight.value, weight.unit)
        } else {
            Weight(weight.value * 2.2046, WeightUnit.Lb)
        }

        WeightUnit.Lb -> if (toUnit == WeightUnit.Lb) {
            Weight(weight.value, weight.unit)
        } else {
            Weight(weight.value * 0.45359237, WeightUnit.Kg)
        }
    }
}

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