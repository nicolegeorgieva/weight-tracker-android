package com.weighttracker

import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe

class UtilsTest : FreeSpec({
    "format number" - {
        "49.45 should be \"49.45\"" {
            formatNumber(49.45) shouldBe "49.45"
        }
    }
})