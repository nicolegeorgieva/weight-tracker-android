package com.weighttracker

import com.weighttracker.domain.glasses
import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe

class WaterTest : FreeSpec({
    "glasses" - {
        "0 liters" {
            glasses(0.0) shouldBe listOf(false, false, false, false)
        }
        "0.25 liters" {
            glasses(0.25) shouldBe listOf(true, false, false, false)
        }
        "0.75 liters" {
            glasses(0.75) shouldBe listOf(true, true, true, false)
        }
        "1 liters" {
            glasses(1.0) shouldBe listOf(true, true, true, true, false, false, false, false)
        }
        "2 liters" {
            glasses(2.0) shouldBe listOf(
                true, true, true, true,
                true, true, true, true,
                false, false, false, false
            )
        }
        "2.5 liters" {
            glasses(2.5) shouldBe listOf(
                true, true, true, true,
                true, true, true, true,
                true, true, false, false
            )
        }
        "3 liters" {
            glasses(3.0) shouldBe listOf(
                true, true, true, true,
                true, true, true, true,
                true, true, true, true
            )
        }
    }
})