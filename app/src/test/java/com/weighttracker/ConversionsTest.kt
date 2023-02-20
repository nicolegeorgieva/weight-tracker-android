package com.weighttracker

import com.weighttracker.domain.convertToKg
import com.weighttracker.domain.convertToM
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
})