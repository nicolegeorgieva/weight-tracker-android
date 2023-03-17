package com.weighttracker

import com.weighttracker.domain.convertHeight
import com.weighttracker.domain.convertWeight
import com.weighttracker.domain.data.Height
import com.weighttracker.domain.data.HeightUnit
import com.weighttracker.domain.data.Weight
import com.weighttracker.domain.data.WeightUnit
import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe

class ConversionsTest : FreeSpec({
    //Convert weight function
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

    //Convert height function
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
})