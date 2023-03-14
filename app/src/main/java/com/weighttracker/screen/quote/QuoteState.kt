package com.weighttracker.screen.quote

import com.weighttracker.domain.data.Quote
import com.weighttracker.network.NetworkError
import com.weighttracker.network.RemoteCall

data class QuoteState(
    val quote: String?,
    val quotesRequest: RemoteCall<NetworkError, List<Quote>>,
)