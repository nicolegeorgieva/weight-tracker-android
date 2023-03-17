package com.weighttracker

import com.weighttracker.domain.convertHeight
import com.weighttracker.domain.convertWater
import com.weighttracker.domain.convertWeight
import com.weighttracker.domain.data.*
import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe

class ConversionsTest : FreeSpec({
    // Convert weight function
    "convert weight value and unit to other unit" - {
        "kg to kg" {
            val res = convertWeight(Weight(50.0, WeightUnit.Kg), WeightUnit.Kg)

            formatNumber(res.value) shouldBe "50"
            res.unit shouldBe WeightUnit.Kg
        }

        "lb to kg" {
            val res = convertWeight(Weight(100.0, WeightUnit.Lb), WeightUnit.Kg)

            formatNumber(res.value) shouldBe "45.36"
        }

        "lb to lb" {
            val res = convertWeight(Weight(100.0, WeightUnit.Lb), WeightUnit.Lb)

            formatNumber(res.value) shouldBe "100"
            res.unit shouldBe WeightUnit.Lb
        }

        "kg to lb" {
            val res = convertWeight(Weight(50.0, WeightUnit.Kg), WeightUnit.Lb)

            formatNumber(res.value) shouldBe "110.23"
            res.unit shouldBe WeightUnit.Lb
        }
    }

    // Convert height function
    "convert height value and unit to other unit" - {
        "m to m" {
            val res = convertHeight(Height(1.8, HeightUnit.M), HeightUnit.M)

            formatNumber(res.value) shouldBe "1.8"
            res.unit shouldBe HeightUnit.M
        }

        "m to ft" {
            val res = convertHeight(Height(1.8, HeightUnit.M), HeightUnit.Ft)

            formatNumber(res.value) shouldBe "5.91"
            res.unit shouldBe HeightUnit.Ft
        }

        "ft to ft" {
            val res = convertHeight(Height(5.91, HeightUnit.Ft), HeightUnit.Ft)

            formatNumber(res.value) shouldBe "5.91"
            res.unit shouldBe HeightUnit.Ft
        }

        "ft to m" {
            val res = convertHeight(Height(5.91, HeightUnit.Ft), HeightUnit.M)

            formatNumber(res.value) shouldBe "1.8"
            res.unit shouldBe HeightUnit.M
        }
    }

    // Convert water function
    "convert water value and unit to other unit" - {
        "l to l" {
            val res = convertWater(Water(1.0, WaterUnit.L), WaterUnit.L)

            formatNumber(res.value) shouldBe "1"
            res.unit shouldBe WaterUnit.L
        }

        "l to gal" {
            val res = convertWater(Water(1.0, WaterUnit.L), WaterUnit.Gal)

            formatNumber(res.value) shouldBe "0.26"
            res.unit shouldBe WaterUnit.Gal
        }

        "gal to gal" {
            val res = convertWater(Water(0.26, WaterUnit.Gal), WaterUnit.Gal)

            formatNumber(res.value) shouldBe "0.26"
            res.unit shouldBe WaterUnit.Gal
        }

        "gal to l" {
            val res = convertWater(Water(0.26, WaterUnit.Gal), WaterUnit.L)

            formatNumber(res.value) shouldBe "0.98"
            res.unit shouldBe WaterUnit.L
        }
    }
})