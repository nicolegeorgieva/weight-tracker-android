package com.weighttracker.screen.quote

sealed interface QuoteEvent {
    data class QuoteInput(val quote: String) : QuoteEvent
}