package com.weighttracker.screen.recipe

import com.weighttracker.domain.data.Recipe
import com.weighttracker.network.NetworkError
import com.weighttracker.network.RemoteCall

data class RecipeState(
//    val page: Int,
    val recipe: RemoteCall<NetworkError, List<Recipe>>
)