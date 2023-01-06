package com.weighttracker.screen.articles

import com.google.gson.annotations.SerializedName

data class ArticlesState(
    val articles: List<Article>
)

data class Article(
    @SerializedName("image")
    val image: String,
    @SerializedName("title")
    val title: String,
    @SerializedName("articleLink")
    val articleLink: String
)