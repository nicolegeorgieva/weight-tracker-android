package com.weighttracker.screen.recipe

import com.weighttracker.network.NetworkError
import com.weighttracker.network.RemoteCall
import com.weighttracker.network.recipe.RecipeResponse

data class RecipeState(
    val recipe: RemoteCall<NetworkError, RecipeResponse>
)