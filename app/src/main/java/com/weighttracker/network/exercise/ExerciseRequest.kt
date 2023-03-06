package com.weighttracker.network.exercise

import arrow.core.Either
import com.google.gson.annotations.SerializedName
import com.weighttracker.network.SimpleRequest
import com.weighttracker.network.httpRequest
import io.ktor.client.request.*
import io.ktor.client.statement.*
import javax.inject.Inject

data class ExerciseRequestInput(
    val muscle: String
)

class ExerciseRequest @Inject constructor(
) : SimpleRequest<ExerciseRequestInput, List<ExerciseResponse>>() {
    override suspend fun request(
        input: ExerciseRequestInput
    ): Either<HttpResponse?, List<ExerciseResponse>> = httpRequest {
        get("https://exerciseapi3.p.rapidapi.com/search/") {
            parameter("primaryMuscle", input.muscle)

            headers {
                set("X-RapidAPI-Key", "eb65cf4deemsh320bcefb59863fcp184ca1jsn13214a644d91")
                set("X-RapidAPI-Host", "exerciseapi3.p.rapidapi.com")
            }
        }
    }
}

data class ExerciseResponse(
    @SerializedName("Name")
    val name: String?,
    @SerializedName("Primary Muscles")
    val primaryMuscles: List<String>?,
    @SerializedName("SecondaryMuscles")
    val secondaryMuscles: List<String>?
)