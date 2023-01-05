package com.weighttracker.network.quotes

import com.weighttracker.base.FlowAction
import com.weighttracker.network.request
import io.ktor.client.request.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class QuotesFlow @Inject constructor(

) : FlowAction<Unit, List<String>>() {
    override fun Unit.createFlow(): Flow<List<String>> = request<QuotesResponse> {
        it.get("fdgdfg")
    }.map { response ->
        response?.quotes ?: emptyList()
    }
}