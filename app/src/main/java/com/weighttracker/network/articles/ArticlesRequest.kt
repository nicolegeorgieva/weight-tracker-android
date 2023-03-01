package com.weighttracker.network.articles

import arrow.core.Either
import com.google.gson.annotations.SerializedName
import com.weighttracker.network.SimpleRequest
import com.weighttracker.network.httpRequest
import io.ktor.client.request.*
import io.ktor.client.statement.*
import javax.inject.Inject

class ArticlesRequest @Inject constructor(
) : SimpleRequest<ArticlesResponse>() {
    override suspend fun request(): Either<HttpResponse?, ArticlesResponse> = httpRequest {
        get("https://raw.githubusercontent.com/nicolegeorgieva/weight-tracker-android/main/articles.json")
    }
}

data class ArticlesResponse(
    @SerializedName("articles")
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