package com.weighttracker.network.recipe

import arrow.core.Either
import com.google.gson.annotations.SerializedName
import com.weighttracker.network.SimpleRequest
import com.weighttracker.network.httpRequest
import io.ktor.client.request.*
import io.ktor.client.statement.*
import javax.inject.Inject

class RecipeRequest @Inject constructor(
) : SimpleRequest<Unit, RecipeResponse>() {
    override suspend fun request(
        input: Unit
    ): Either<HttpResponse?, RecipeResponse> = httpRequest {
        get("https://yummly2.p.rapidapi.com/feeds/list") {
            parameter("limit", 24)
            parameter("start", 0)

            headers {
                set("X-RapidAPI-Key", "eb65cf4deemsh320bcefb59863fcp184ca1jsn13214a644d91")
                set("X-RapidAPI-Host", "yummly2.p.rapidapi.com")
            }
        }
    }
}

data class RecipeResponse(
    @SerializedName("feed")
    val feed: List<FeedItem>
)

data class FeedItem(
    @SerializedName("display")
    val display: Display?
)

data class Display(
    @SerializedName("displayName")
    val displayName: String?,
    @SerializedName("images")
    val images: List<String>?,
    @SerializedName("source")
    val source: Source?
)

data class Source(
    @SerializedName("sourceRecipeUrl")
    val sourceRecipeUrl: String
)