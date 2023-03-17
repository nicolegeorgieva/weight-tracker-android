package com.weighttracker

import com.weighttracker.domain.NormalWeightRange
import com.weighttracker.domain.calculateNormalWeightRange
import com.weighttracker.domain.data.Height
import com.weighttracker.domain.data.HeightUnit
import com.weighttracker.domain.data.Weight
import com.weighttracker.domain.data.WeightUnit
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
                    Height(1.8, HeightUnit.M), Weight(98.0, WeightUnit.Kg)
                )
            ) shouldBe Pair("59.94", "81")
        }
    }
})

