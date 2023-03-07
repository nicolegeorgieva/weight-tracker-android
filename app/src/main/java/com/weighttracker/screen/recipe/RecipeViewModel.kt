package com.weighttracker.screen.recipe

import com.weighttracker.base.SimpleFlowViewModel
import com.weighttracker.domain.network.mapRecipeResponse
import com.weighttracker.network.RemoteCall
import com.weighttracker.network.mapSuccess
import com.weighttracker.network.recipe.RecipeRequest
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

@HiltViewModel
class RecipeViewModel @Inject constructor(
    private val recipeRequest: RecipeRequest,
) : SimpleFlowViewModel<RecipeState, RecipeEvent>() {
    override val initialUi = RecipeState(
        recipe = RemoteCall.Loading
    )

    override val uiFlow: Flow<RecipeState> = combine(
        recipeRequest.flow(Unit),
        flowOf(Unit)
    ) { recipeRemoteCall, _ ->
        RecipeState(
            recipe = recipeRemoteCall.mapSuccess {
                mapRecipeResponse(it)
            }
        )
    }

    override suspend fun handleEvent(event: RecipeEvent) {
        when (event) {
            RecipeEvent.RetryRecipeRequest -> recipeRequest.retry()
        }
    }
}