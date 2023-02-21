package com.weighttracker

import com.weighttracker.domain.calculateNormalWeightRange
import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe

fun formatPair(notFormatted: Pair<Double, Double>): Pair<String, String> {
    val formatFirst = formatNumber(notFormatted.first)
    val formatSecond = formatNumber(notFormatted.second)
    return Pair(formatFirst, formatSecond)
}

class NormalWeightRangeTest : FreeSpec({
    "normal weight range" - {
        "for 98kg and 1.8m" {
            formatPair(
                calculateNormalWeightRange(
                    1.8, true, true
                )
            ) shouldBe Pair("59.94", "80.68")
        }
    }
})

