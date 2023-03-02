package com.weighttracker.network.quotes

import arrow.core.Either
import com.google.gson.annotations.SerializedName
import com.weighttracker.network.SimpleRequest
import com.weighttracker.network.httpRequest
import io.ktor.client.request.*
import io.ktor.client.statement.*
import javax.inject.Inject

class QuotesRequest @Inject constructor(
) : SimpleRequest<Unit, QuotesResponse>() {
    override suspend fun request(
        input: Unit
    ): Either<HttpResponse?, QuotesResponse> = httpRequest {
        get("https://raw.githubusercontent.com/nicolegeorgieva/weight-tracker-android/main/quotes.json")
    }
}

data class QuotesResponse(
    @SerializedName("quotes")
    val quotes: List<String>
)