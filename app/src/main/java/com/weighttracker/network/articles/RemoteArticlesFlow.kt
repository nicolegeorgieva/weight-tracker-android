package com.weighttracker.network.articles

import com.weighttracker.base.FlowAction
import com.weighttracker.screen.articles.Article
import io.ktor.client.request.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class RemoteArticlesFlow @Inject constructor(
) : FlowAction<Unit, List<Article>>() {
    override fun Unit.createFlow(): Flow<List<Article>> =
        com.weighttracker.network.request<ArticlesResponse> {
            it.get("https://raw.githubusercontent.com/nicolegeorgieva/weight-tracker-android/main/quotes.json")
        }.map { response ->
            response?.articles ?: emptyList()
        }
}