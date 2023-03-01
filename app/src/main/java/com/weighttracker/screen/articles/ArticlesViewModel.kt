package com.weighttracker.screen.articles

import com.weighttracker.base.SimpleFlowViewModel
import com.weighttracker.network.NetworkError
import com.weighttracker.network.RemoteCall
import com.weighttracker.network.articles.ArticlesRequest
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

@HiltViewModel
class ArticlesViewModel @Inject constructor(
    private val articlesRequest: ArticlesRequest,
) : SimpleFlowViewModel<ArticlesState, ArticlesEvent>() {
    override val initialUi = ArticlesState(
        articles = RemoteCall.Error(NetworkError.Generic)
    )

    override val uiFlow: Flow<ArticlesState> = combine(
        articlesRequest.flow,
        flowOf(Unit)
    ) { articles, _ ->
        ArticlesState(
            articles = articles
        )
    }

    override suspend fun handleEvent(event: ArticlesEvent) {
        when (event) {
            ArticlesEvent.RetryArticlesRequest -> articlesRequest.retry()
        }
    }
}