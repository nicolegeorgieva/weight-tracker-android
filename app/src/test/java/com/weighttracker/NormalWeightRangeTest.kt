package com.weighttracker

import com.weighttracker.domain.NormalWeightRange
import com.weighttracker.domain.calculateNormalWeightRange
import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe

fun formatNormalWeight(notFormatted: NormalWeightRange): Pair<String, String> {
    val formatFirst = formatNumber(notFormatted.minWeight)
    val formatSecond = formatNumber(notFormatted.maxWeight)
    return Pair(formatFirst, formatSecond)
}

class NormalWeightRangeTest : FreeSpec({
    "normal weight range" - {
        "for 98kg and 1.8m" {
            formatNormalWeight(
                calculateNormalWeightRange(
                    1.8, true, true
                )
            ) shouldBe Pair("59.94", "80.68")
        }
    }
})

