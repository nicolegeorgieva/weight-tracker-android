package com.weighttracker.screen.articles

import com.weighttracker.base.SimpleFlowViewModel
import com.weighttracker.network.articles.RemoteArticlesFlow
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import javax.inject.Inject

@HiltViewModel
class ArticlesViewModel @Inject constructor(
    private val remoteArticlesFlow: RemoteArticlesFlow
) : SimpleFlowViewModel<ArticlesState, ArticlesEvent>() {
    override val initialUi = ArticlesState(
        articles = emptyList()
    )

    override val uiFlow: Flow<ArticlesState> = combine(
        remoteArticlesFlow(Unit),
        remoteArticlesFlow(Unit)
    ) { articles, _ ->
        ArticlesState(
            articles = articles
        )
    }

    override suspend fun handleEvent(event: ArticlesEvent) {
        when (event) {

            else -> {}
        }
    }
}