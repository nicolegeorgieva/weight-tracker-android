package com.weighttracker.screen.quote

import com.weighttracker.network.NetworkError
import com.weighttracker.network.RemoteCall
import com.weighttracker.network.calories.NutrientsResponse
import com.weighttracker.network.quotes.QuotesResponse

data class QuoteState(
    val quote: String?,
    val quotesRequest: RemoteCall<NetworkError, QuotesResponse>,
    val nutrientsRequest: RemoteCall<NetworkError, NutrientsResponse>
)