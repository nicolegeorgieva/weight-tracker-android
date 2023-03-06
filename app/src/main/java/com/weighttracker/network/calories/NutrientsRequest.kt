package com.weighttracker.network.calories

import arrow.core.Either
import com.google.gson.annotations.SerializedName
import com.weighttracker.network.SimpleRequest
import com.weighttracker.network.httpRequest
import com.weighttracker.screen.nutrients.FoodSize
import io.ktor.client.request.*
import io.ktor.client.statement.*
import javax.inject.Inject

data class NutrientRequestInput(
    val quantity: Int,
    val food: String,
    val foodSize: FoodSize
)

class NutrientsRequest @Inject constructor(
) : SimpleRequest<NutrientRequestInput, NutrientsResponse>() {
    override suspend fun request(
        input: NutrientRequestInput
    ): Either<HttpResponse?, NutrientsResponse> = httpRequest {
        get("https://edamam-edamam-nutrition-analysis.p.rapidapi.com/api/nutrition-data") {
            parameter("ingr", inputToString(input))

            headers {
                set("X-RapidAPI-Key", "eb65cf4deemsh320bcefb59863fcp184ca1jsn13214a644d91")
                set("X-RapidAPI-Host", "edamam-edamam-nutrition-analysis.p.rapidapi.com")
            }
        }
    }

    private fun inputToString(input: NutrientRequestInput): String {
        val size = when (input.foodSize) {
            FoodSize.Small -> "small"
            FoodSize.Medium -> "medium"
            FoodSize.Large -> "large"
        }

        return "${input.quantity} $size ${input.food}"
    }
}

data class NutrientsResponse(
    @SerializedName("calories")
    val calories: Int,
    @SerializedName("totalNutrients")
    val nutrients: Nutrients,
    @SerializedName("totalWeight")
    val totalWeight: Int
)

data class Nutrients(
    @SerializedName("FAT")
    val fat: MacroNutrient,
    @SerializedName("PROCNT")
    val protein: MacroNutrient,
    @SerializedName("CHOCDF")
    val carbs: MacroNutrient,
    @SerializedName("FIBTG")
    val fiber: MacroNutrient
)

data class MacroNutrient(
    @SerializedName("quantity")
    val quantity: Double,
    @SerializedName("unit")
    val unit: String
)

