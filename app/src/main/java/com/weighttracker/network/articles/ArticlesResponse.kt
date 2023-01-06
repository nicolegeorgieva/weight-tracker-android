package com.weighttracker.network.articles

import com.google.gson.annotations.SerializedName
import com.weighttracker.screen.articles.Article

data class ArticlesResponse(
    @SerializedName("articles")
    val articles: List<Article>
)