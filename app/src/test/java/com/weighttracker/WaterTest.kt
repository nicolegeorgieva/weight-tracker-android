package com.weighttracker

import com.weighttracker.domain.glasses
import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe

class WaterTest : FreeSpec({
    "glasses" - {
        "0 liters" {
            glasses(0.0) shouldBe listOf(false, false, false, false)
        }
        "3 liters" {
            glasses(3.0) shouldBe listOf(
                true, true, true, true,
                true, true, true, true,
                true, true, true, true
            )
        }
        "2 liters" {
            glasses(2.0) shouldBe listOf(
                true, true, true, true,
                true, true, true, true,
                false, false, false, false
            )
        }
    }
})