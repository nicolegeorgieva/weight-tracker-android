package com.weighttracker.screen.articles

sealed interface ArticlesEvent {
    object RetryArticlesRequest : ArticlesEvent
}