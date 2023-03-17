package com.weighttracker

import com.weighttracker.domain.convertToM
import com.weighttracker.domain.convertWeight
import com.weighttracker.domain.data.Weight
import com.weighttracker.domain.data.WeightUnit
import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe

class ConversionsTest : FreeSpec({
    "convert to m" - {
        "m to m" {
            formatNumber(convertToM(1.58, true)) shouldBe "1.58"
        }

        "feet to m" {
            formatNumber(convertToM(5.7, false)) shouldBe "1.74"
        }
    }

    //Convert weight function

    "convert value and unit to other unit" - {
        "kg to kg" {
            val converted = convertWeight(Weight(50.0, WeightUnit.Kg), WeightUnit.Kg)

            formatNumber(converted.value) shouldBe "50"
            converted.unit shouldBe WeightUnit.Kg
        }

        "lb to kg" {
            val converted = convertWeight(Weight(100.0, WeightUnit.Lb), WeightUnit.Kg)

            formatNumber(converted.value) shouldBe "45.36"
        }

        "lb to lb" {
            val converted = convertWeight(Weight(100.0, WeightUnit.Lb), WeightUnit.Lb)

            formatNumber(converted.value) shouldBe "100"
            converted.unit shouldBe WeightUnit.Lb
        }

        "kg to lb" {
            val converted = convertWeight(Weight(50.0, WeightUnit.Kg), WeightUnit.Lb)

            formatNumber(converted.value) shouldBe "110.23"
            converted.unit shouldBe WeightUnit.Lb
        }
    }
})