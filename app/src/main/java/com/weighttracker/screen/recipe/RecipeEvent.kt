package com.weighttracker.screen.recipe

sealed interface RecipeEvent {
    object ChangePageBack : RecipeEvent
    object ChangePageNext : RecipeEvent
    object RetryRecipeRequest : RecipeEvent
}