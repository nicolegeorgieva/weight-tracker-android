package com.weighttracker.screen.quote

import com.weighttracker.base.SimpleFlowViewModel
import com.weighttracker.persistence.quote.QuoteFlow
import com.weighttracker.persistence.quote.WriteQuoteAct
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import javax.inject.Inject

@HiltViewModel
class QuoteViewModel @Inject constructor(
    private val quoteFlow: QuoteFlow,
    private val writeQuoteAct: WriteQuoteAct
) : SimpleFlowViewModel<QuoteState, QuoteEvent>() {
    override val initialUi = QuoteState(
        quote = "",
        quoteList = emptyList()
    )

    override val uiFlow: Flow<QuoteState> = combine(
        quoteFlow(Unit),
        quoteFlow(Unit)
    ) { quote, _ ->
        QuoteState(
            quote = quote,
            quoteList = listOf(
                "A journey of thousand miles starts with one step",
                "hi",
                "sdflldsfk"
            )
        )
    }

    override suspend fun handleEvent(event: QuoteEvent) {
        when (event) {
            is QuoteEvent.QuoteChange -> {
                writeQuoteAct(event.quote)
            }
            QuoteEvent.Clear -> {
                writeQuoteAct("")
            }
        }
    }
}