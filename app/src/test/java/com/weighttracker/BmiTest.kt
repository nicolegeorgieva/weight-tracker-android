package com.weighttracker

import com.weighttracker.domain.calculateBmi
import com.weighttracker.domain.data.Height
import com.weighttracker.domain.data.HeightUnit
import com.weighttracker.domain.data.Weight
import com.weighttracker.domain.data.WeightUnit
import com.weighttracker.domain.formatBmi
import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe

class BmiTest : FreeSpec({
    "bmi calculation with formatting" - {
        "bmi with kg and m" {
            formatBmi(
                calculateBmi(Weight(57.4, WeightUnit.Kg), Height(1.58, HeightUnit.M))
            ) shouldBe "23"
        }

        "bmi with kg and feet" {
            formatBmi(
                calculateBmi(Weight(57.4, WeightUnit.Kg), Height(6.0, HeightUnit.Ft))
            ) shouldBe "17.2"
        }

        "bmi with lb and m" {
            formatBmi(
                calculateBmi(Weight(160.0, WeightUnit.Lb), Height(1.58, HeightUnit.M))
            ) shouldBe "29.1"
        }

        "bmi with lb and feet" {
            formatBmi(
                calculateBmi(Weight(160.0, WeightUnit.Lb), Height(5.0, HeightUnit.Ft))
            ) shouldBe "31.2"
        }
    }

    "format bmi" - {
        "30.0 should be 30" {
            formatBmi(30.0) shouldBe "30"
        }

        "29.89 should be 29.9" {
            formatBmi(29.89) shouldBe "29.9"
        }

        "30.01 shouldBe 30" {
            formatBmi(30.01) shouldBe "30"
        }

        "38.43 shouldBe 38.4" {
            formatBmi(38.43) shouldBe "38.4"
        }

        "38.45 shouldBe 38.5" {
            formatBmi(38.45) shouldBe "38.5"
        }
    }
})