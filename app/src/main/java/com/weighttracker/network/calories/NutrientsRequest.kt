package com.weighttracker.network.calories

import arrow.core.Either
import com.google.gson.annotations.SerializedName
import com.weighttracker.network.SimpleRequest
import com.weighttracker.network.httpRequest
import io.ktor.client.request.*
import io.ktor.client.statement.*
import javax.inject.Inject

class NutrientsRequest @Inject constructor(
) : SimpleRequest<String, NutrientsResponse>() {
    override suspend fun request(
        input: String
    ): Either<HttpResponse?, NutrientsResponse> = httpRequest {
        get("https://edamam-edamam-nutrition-analysis.p.rapidapi.com/api/nutrition-data") {
            parameter("ingr", input)

            headers {
                set("X-RapidAPI-Key", "eb65cf4deemsh320bcefb59863fcp184ca1jsn13214a644d91")
                set("X-RapidAPI-Host", "edamam-edamam-nutrition-analysis.p.rapidapi.com")
            }
        }
    }
}

data class NutrientsResponse(
    @SerializedName("calories")
    val calories: Int,
    @SerializedName("totalNutrients")
    val nutrients: Nutrients
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

