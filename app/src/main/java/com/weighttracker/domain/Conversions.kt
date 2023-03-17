package com.weighttracker.domain

import com.weighttracker.domain.data.Weight
import com.weighttracker.domain.data.WeightUnit

//"50 kg", to unit "lb" : "100 lb"
fun convertWeight(weight: Weight, toUnit: WeightUnit): Weight {
    return when (weight.unit) {
        WeightUnit.Kg -> if (toUnit == WeightUnit.Kg) {
            weight
        } else {
            Weight(weight.value * 2.2046, WeightUnit.Lb)
        }

        WeightUnit.Lb -> if (toUnit == WeightUnit.Lb) {
            weight
        } else {
            Weight(weight.value * 0.45359237, WeightUnit.Kg)
        }
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