package com.weighttracker

import com.weighttracker.domain.convert
import com.weighttracker.domain.convertToKg
import com.weighttracker.domain.convertToM
import com.weighttracker.domain.data.Weight
import com.weighttracker.domain.data.WeightUnit
import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe

class ConversionsTest : FreeSpec({
    "convert to kg" - {
        "kg to kg" {
            formatNumber(convertToKg(50.0, true)) shouldBe "50"
        }

        "lb to kg" {
            formatNumber(convertToKg(130.0, false)) shouldBe "58.97"
        }
    }

    "convert to m" - {
        "m to m" {
            formatNumber(convertToM(1.58, true)) shouldBe "1.58"
        }

        "feet to m" {
            formatNumber(convertToM(5.7, false)) shouldBe "1.74"
        }
    }

    //universal & fullest convert function

    "convert value and unit to value and kg" - {
        "kg to kg" {
            val converted = convert(Weight(50.0, WeightUnit.Kg), WeightUnit.Kg)

            formatNumber(converted.value) shouldBe "50"
            converted.unit shouldBe WeightUnit.Kg
        }

        "lb to kg" {
            val converted = convert(Weight(100.0, WeightUnit.Lb), WeightUnit.Kg)

            formatNumber(converted.value) shouldBe "45.36"
        }
    }

    "convert value and unit to value and lb" - {
        "lb to lb" {
            val converted = convert(Weight(100.0, WeightUnit.Lb), WeightUnit.Lb)

            formatNumber(converted.value) shouldBe "100"
            converted.unit shouldBe WeightUnit.Lb
        }

        "kg to lb" {
            val converted = convert(Weight(50.0, WeightUnit.Kg), WeightUnit.Lb)

            formatNumber(converted.value) shouldBe "110.23"
            converted.unit shouldBe WeightUnit.Lb
        }
    }
})