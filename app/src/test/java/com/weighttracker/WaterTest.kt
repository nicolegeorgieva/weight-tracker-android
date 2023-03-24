package com.weighttracker

import com.weighttracker.domain.GLASS_IN_GAL
import com.weighttracker.domain.GLASS_IN_L
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
            GLASS_IN_L shouldBe 0.25
            glasses(Water(GLASS_IN_L, WaterUnit.L)) shouldBe listOf(true, false, false, false)
        }

        "0.75 liters" {
            3 * GLASS_IN_L shouldBe 0.75
            glasses(Water(3 * GLASS_IN_L, WaterUnit.L)) shouldBe listOf(true, true, true, false)
        }

        "1 liters" {
            4 * GLASS_IN_L shouldBe 1.0
            glasses(Water(4 * GLASS_IN_L, WaterUnit.L)) shouldBe listOf(
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
            8 * GLASS_IN_L shouldBe 2.0
            glasses(Water(8 * GLASS_IN_L, WaterUnit.L)) shouldBe listOf(
                true, true, true, true,
                true, true, true, true,
                false, false, false, false
            )
        }

        "2.5 liters" {
            10 * GLASS_IN_L shouldBe 2.5
            glasses(Water(10 * GLASS_IN_L, WaterUnit.L)) shouldBe listOf(
                true, true, true, true,
                true, true, true, true,
                true, true, false, false
            )
        }

        "3 liters" {
            12 * GLASS_IN_L shouldBe 3.0
            glasses(Water(12 * GLASS_IN_L, WaterUnit.L)) shouldBe listOf(
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

        "0.0660430131 gal" {
            GLASS_IN_GAL shouldBe 0.0660430131
            glasses(Water(GLASS_IN_GAL, WaterUnit.Gal)) shouldBe listOf(
                true,
                false,
                false,
                false
            )
        }

        "0.19812903930000003 gal" {
            3 * GLASS_IN_GAL shouldBe 0.19812903930000003
            glasses(Water(3 * GLASS_IN_GAL, WaterUnit.Gal)) shouldBe listOf(true, true, true, false)
        }

        "0.2641720524 gal" {
            4 * GLASS_IN_GAL shouldBe 0.2641720524
            glasses(Water(4 * GLASS_IN_GAL, WaterUnit.Gal)) shouldBe listOf(
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

        "0.5283441048 gal" {
            8 * GLASS_IN_GAL shouldBe 0.5283441048
            glasses(Water(8 * GLASS_IN_GAL, WaterUnit.Gal)) shouldBe listOf(
                true, true, true, true,
                true, true, true, true,
                false, false, false, false
            )
        }

        "0.660430131 gal" {
            10 * GLASS_IN_GAL shouldBe 0.660430131
            glasses(Water(10 * GLASS_IN_GAL, WaterUnit.Gal)) shouldBe listOf(
                true, true, true, true,
                true, true, true, true,
                true, true, false, false
            )
        }

        "0.7925161572000001 gal" {
            12 * GLASS_IN_GAL shouldBe 0.7925161572000001
            glasses(Water(12 * GLASS_IN_GAL, WaterUnit.Gal)) shouldBe listOf(
                true, true, true, true,
                true, true, true, true,
                true, true, true, true
            )
        }
    }
})