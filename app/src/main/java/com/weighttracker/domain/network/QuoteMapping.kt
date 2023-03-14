package com.weighttracker.domain.network

import com.weighttracker.domain.data.Quote
import com.weighttracker.network.quotes.QuotesResponse

fun mapQuoteResponse(response: QuotesResponse): List<Quote> {
    return response.quotes.mapNotNull { quoteText ->
        Quote(
            quote = quoteText.takeIf { it.isNotBlank() } ?: return@mapNotNull null
        )
    }
}