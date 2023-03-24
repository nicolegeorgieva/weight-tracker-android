package com.weighttracker

import com.weighttracker.domain.data.Water
import com.weighttracker.domain.data.WaterUnit
import com.weighttracker.domain.glasses
import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe

class WaterTest : FreeSpec({
    "glasses for liters" - {
        "0 liters" {
            glasses(Water(0.0, WaterUnit.L)) shouldBe listOf(false, false, false, false)
        }

        "0.25 liters" {
            glasses(Water(0.25, WaterUnit.L)) shouldBe listOf(true, false, false, false)
        }

        "0.75 liters" {
            glasses(Water(0.75, WaterUnit.L)) shouldBe listOf(true, true, true, false)
        }

        "1 liters" {
            glasses(Water(1.0, WaterUnit.L)) shouldBe listOf(
                true,
                true,
                true,
                true,
                false,
                false,
                false,
                false
            )
        }

        "2 liters" {
            glasses(Water(2.0, WaterUnit.L)) shouldBe listOf(
                true, true, true, true,
                true, true, true, true,
                false, false, false, false
            )
        }

        "2.5 liters" {
            glasses(Water(2.5, WaterUnit.L)) shouldBe listOf(
                true, true, true, true,
                true, true, true, true,
                true, true, false, false
            )
        }

        "3 liters" {
            glasses(Water(3.0, WaterUnit.L)) shouldBe listOf(
                true, true, true, true,
                true, true, true, true,
                true, true, true, true
            )
        }
    }

    "glasses for gallons" - {
        "0 gal" {
            glasses(Water(0.0, WaterUnit.Gal)) shouldBe listOf(false, false, false, false)
        }

        "0.07 gal" {
            glasses(Water(0.07, WaterUnit.Gal)) shouldBe listOf(true, false, false, false)
        }

        "0.2 gal" {
            glasses(Water(0.2, WaterUnit.Gal)) shouldBe listOf(true, true, true, false)
        }

        "0.27 gal" {
            glasses(Water(0.27, WaterUnit.Gal)) shouldBe listOf(
                true,
                true,
                true,
                true,
                false,
                false,
                false,
                false
            )
        }

        "0.53 gal" {
            glasses(Water(0.53, WaterUnit.Gal)) shouldBe listOf(
                true, true, true, true,
                true, true, true, true,
                false, false, false, false
            )
        }

        "0.661 gal" {
            glasses(Water(0.661, WaterUnit.Gal)) shouldBe listOf(
                true, true, true, true,
                true, true, true, true,
                true, true, false, false
            )
        }

        "0.793 gal" {
            glasses(Water(0.793, WaterUnit.Gal)) shouldBe listOf(
                true, true, true, true,
                true, true, true, true,
                true, true, true, true
            )
        }
    }
})