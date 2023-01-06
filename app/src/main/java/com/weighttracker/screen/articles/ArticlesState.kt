package com.weighttracker.screen.articles

import com.google.gson.annotations.SerializedName

data class ArticlesState(
    val articles: List<Article>
)

data class Article(
    @SerializedName("title")
    val title: String,
    @SerializedName("image")
    val image: String,
    @SerializedName("articleLink")
    val articleLink: String
)