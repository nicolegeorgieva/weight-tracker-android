package com.weighttracker.screen.recipe

import com.weighttracker.base.SimpleFlowViewModel
import com.weighttracker.domain.network.mapRecipeResponse
import com.weighttracker.flattenLatest
import com.weighttracker.network.NetworkError
import com.weighttracker.network.RemoteCall
import com.weighttracker.network.mapSuccess
import com.weighttracker.network.recipe.Page
import com.weighttracker.network.recipe.RecipeRequest
import com.weighttracker.network.recipe.RecipeResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import javax.inject.Inject

@HiltViewModel
class RecipeViewModel @Inject constructor(
    private val recipeRequest: RecipeRequest,
) : SimpleFlowViewModel<RecipeState, RecipeEvent>() {
    override val initialUi = RecipeState(
        recipe = RemoteCall.Loading,
        page = 1
    )

    private val selectedPageFlow = MutableStateFlow<Int>(initialUi.page)

    private val recipeRequestFlow: Flow<RemoteCall<NetworkError, RecipeResponse>> =
        combine(
            selectedPageFlow, selectedPageFlow
        ) { selectedPage, _ ->
            recipeRequest.flow(input = Page(pageNumber = selectedPage))
        }.flattenLatest()

    override val uiFlow: Flow<RecipeState> = combine(
        recipeRequestFlow,
        selectedPageFlow
    ) { recipeRemoteCall, selectedPage ->
        RecipeState(
            recipe = recipeRemoteCall.mapSuccess {
                mapRecipeResponse(it)
            },
            page = selectedPage
        )
    }

    override suspend fun handleEvent(event: RecipeEvent) {
        when (event) {
            RecipeEvent.RetryRecipeRequest -> recipeRequest.retry()
            RecipeEvent.ChangePageBack -> selectedPageFlow.value = selectedPageFlow.value - 1
            RecipeEvent.ChangePageNext -> selectedPageFlow.value = selectedPageFlow.value + 1
        }
    }
}