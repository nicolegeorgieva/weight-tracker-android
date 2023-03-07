package com.weighttracker.domain.network

import com.weighttracker.domain.data.Recipe
import com.weighttracker.isNotBlank
import com.weighttracker.network.recipe.RecipeResponse

fun mapRecipeResponse(response: RecipeResponse): List<Recipe> {
    return response.feed.mapNotNull { feedItem ->
        val name = feedItem.display?.displayName?.takeIf { it.isNotBlank() }
            ?: return@mapNotNull null

        val link = feedItem.display.source?.sourceRecipeUrl?.takeIf { it.isNotBlank() }
            ?: return@mapNotNull null

        if (link.contains("https://www.yummly.com/private/")) {
            return@mapNotNull null
        }

        Recipe(
            image = feedItem.display.images?.firstOrNull().takeIf { it.isNotBlank() }
                ?: return@mapNotNull null,
            title = name,
            link = link
        )
    }
}