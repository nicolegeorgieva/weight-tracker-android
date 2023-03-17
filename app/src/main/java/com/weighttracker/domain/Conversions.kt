package com.weighttracker.domain

import com.weighttracker.domain.data.*

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

/**
 * "1 l", to unit "gal" : "0.26 gal"
 */
fun convertWater(water: Water, toUnit: WaterUnit): Water {
    return when (water.unit) {
        // l to l
        WaterUnit.L -> if (toUnit == WaterUnit.L) water
        // l to gal
        else Water(water.value * 0.264172, WaterUnit.Gal)

        // gal to gal
        WaterUnit.Gal -> if (toUnit == WaterUnit.Gal) water
        // gal to l
        else Water(water.value * 3.785411784, WaterUnit.L)
    }
}