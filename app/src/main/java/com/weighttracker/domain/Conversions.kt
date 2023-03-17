package com.weighttracker.domain

import com.weighttracker.domain.data.Height
import com.weighttracker.domain.data.HeightUnit
import com.weighttracker.domain.data.Weight
import com.weighttracker.domain.data.WeightUnit

/**
 * "50 kg", to unit "lb" : "110.23 lb"
 */
fun convertWeight(weight: Weight, toUnit: WeightUnit): Weight {
    return when (weight.unit) {
        WeightUnit.Kg -> if (toUnit == WeightUnit.Kg) weight
        else Weight(weight.value * 2.2046, WeightUnit.Lb)

        WeightUnit.Lb -> if (toUnit == WeightUnit.Lb) weight
        else Weight(weight.value * 0.45359237, WeightUnit.Kg)
    }
}

/**
 * "1.8 m", to unit "ft" : "5.9 ft"
 */
fun convertHeight(height: Height, toUnit: HeightUnit): Height {
    return when (height.unit) {
        HeightUnit.M -> if (toUnit == HeightUnit.M) height
        else Height(height.value * 3.28084, HeightUnit.Ft)

        HeightUnit.Ft -> if (toUnit == HeightUnit.Ft) height
        else Height(height.value * 0.3048, HeightUnit.M)
    }
}