package com.weighttracker.network.quotes

import arrow.core.Either
import com.weighttracker.base.FlowAction
import com.weighttracker.network.httpRequest
import io.ktor.client.request.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class RemoteQuotesFlow @Inject constructor(
) : FlowAction<Unit, List<String>>() {
    override fun Unit.createFlow(): Flow<List<String>> = flow {
        when (val res = httpRequest<QuotesResponse> {
            get("https://raw.githubusercontent.com/nicolegeorgieva/weight-tracker-android/main/quotes.json")
        }) {
            is Either.Left -> emit(emptyList())
            is Either.Right -> emit(res.value.quotes)
        }
    }
}