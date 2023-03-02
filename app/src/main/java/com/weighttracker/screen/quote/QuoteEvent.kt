package com.weighttracker.screen.quote

sealed interface QuoteEvent {
    data class QuoteChange(val quote: String) : QuoteEvent
    object Clear : QuoteEvent
    object RetryQuotesRequest : QuoteEvent
}