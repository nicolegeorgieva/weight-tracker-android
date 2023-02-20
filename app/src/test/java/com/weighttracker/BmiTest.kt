package com.weighttracker

import com.weighttracker.domain.calculateBmi
import com.weighttracker.domain.formatBmi
import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe

class BmiTest : FreeSpec({
    "bmi calculation" - {
        "bmi with kg and m" {
            formatBmi(
                calculateBmi(57.4, 1.58, kgSelected = true, mSelected = true)
            ) shouldBe "23"
        }

        "bmi with kg and feet" {

        }

        "bmi with lb and m" {

        }

        "bmi with lb and feet" {

        }
    }
})