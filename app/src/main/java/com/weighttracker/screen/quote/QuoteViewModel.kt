package com.weighttracker.screen.quote

import com.weighttracker.base.SimpleFlowViewModel
import com.weighttracker.network.quotes.RemoteQuotesFlow
import com.weighttracker.persistence.datastore.quote.QuoteFlow
import com.weighttracker.persistence.datastore.quote.WriteQuoteAct
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import javax.inject.Inject

@HiltViewModel
class QuoteViewModel @Inject constructor(
    private val quoteFlow: QuoteFlow,
    private val writeQuoteAct: WriteQuoteAct,
    private val remoteQuotesFlow: RemoteQuotesFlow,
) : SimpleFlowViewModel<QuoteState, QuoteEvent>() {
    override val initialUi = QuoteState(
        //while loading
        quote = null,
        quoteList = emptyList()
    )

    override val uiFlow: Flow<QuoteState> = combine(
        quoteFlow(Unit),
        remoteQuotesFlow(Unit),
    ) { quote, remoteQuotes ->
        QuoteState(
            quote = quote,
            quoteList = remoteQuotes
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