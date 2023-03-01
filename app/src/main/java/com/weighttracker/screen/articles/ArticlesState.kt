package com.weighttracker.screen.articles

import com.weighttracker.network.NetworkError
import com.weighttracker.network.RemoteCall
import com.weighttracker.network.articles.ArticlesResponse

data class ArticlesState(
    val articles: RemoteCall<NetworkError, ArticlesResponse>
)