package com.weighttracker.screen.recipe

sealed interface RecipeEvent {
    object RetryRecipeRequest : RecipeEvent
}