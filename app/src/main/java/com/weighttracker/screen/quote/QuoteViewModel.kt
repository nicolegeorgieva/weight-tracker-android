package com.weighttracker.screen.quote

import com.weighttracker.base.SimpleFlowViewModel
import com.weighttracker.network.RemoteCall
import com.weighttracker.network.calories.NutrientsRequest
import com.weighttracker.network.quotes.QuotesRequest
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
    private val quotesRequest: QuotesRequest,
    private val nutrientsRequest: NutrientsRequest
) : SimpleFlowViewModel<QuoteState, QuoteEvent>() {
    override val initialUi = QuoteState(
        //while loading
        quote = null,
        quotesRequest = RemoteCall.Loading,
    )

    override val uiFlow: Flow<QuoteState> = combine(
        quoteFlow(Unit),
        quotesRequest.flow(Unit),
    ) { quote, quotesRequest ->
        QuoteState(
            quote = quote,
            quotesRequest = quotesRequest,
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
            QuoteEvent.RetryQuotesRequest -> quotesRequest.retry()
        }
    }
}