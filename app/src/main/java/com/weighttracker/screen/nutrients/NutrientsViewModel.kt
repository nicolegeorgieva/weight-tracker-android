package com.weighttracker.screen.nutrients

import com.weighttracker.base.SimpleFlowViewModel
import com.weighttracker.domain.network.mapNutrientsResponse
import com.weighttracker.flattenLatest
import com.weighttracker.network.NetworkError
import com.weighttracker.network.RemoteCall
import com.weighttracker.network.mapSuccess
import com.weighttracker.network.nutrients.NutrientRequestInput
import com.weighttracker.network.nutrients.NutrientsRequest
import com.weighttracker.network.nutrients.NutrientsResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

@HiltViewModel
class NutrientsViewModel @Inject constructor(
    private val nutrientsRequest: NutrientsRequest,
) : SimpleFlowViewModel<NutrientsState, NutrientsEvent>() {
    override val initialUi = NutrientsState(
        quantity = 1,
        foods = emptyList(),
        selectedFood = null,
        selectedSize = FoodSize.Medium,
        nutrientsRequest = RemoteCall.Loading,
    )

    private val foodsFlow = flowOf(
        listOf(
            "rice",
            "bread",
            "chicken"
        )
    )

    private val quantityFlow = MutableStateFlow(initialUi.quantity)
    private val selectedFoodFlow = MutableStateFlow<String?>(initialUi.selectedFood)
    private val selectedSizeFlow = MutableStateFlow(initialUi.selectedSize)

    private val nutrientsRequestFlow: Flow<RemoteCall<NetworkError, NutrientsResponse>?> =
        combine(
            quantityFlow, selectedFoodFlow, selectedSizeFlow
        ) { quantity, selectedFood, selectedSize ->
            if (quantity == null || selectedFood == null) {
                flowOf(null)
            } else {
                val input = NutrientRequestInput(
                    quantity = quantity,
                    food = selectedFood,
                    foodSize = selectedSize
                )
                nutrientsRequest.flow(input = input)
            }
        }.flattenLatest()

    override val uiFlow: Flow<NutrientsState> = combine(
        quantityFlow,
        foodsFlow,
        selectedFoodFlow,
        selectedSizeFlow,
        nutrientsRequestFlow,
    ) { quantity, foods, selectedFood, selectedSize, request ->
        NutrientsState(
            quantity = quantity,
            foods = foods,
            selectedFood = selectedFood,
            selectedSize = selectedSize,
            nutrientsRequest = request?.mapSuccess {
                mapNutrientsResponse(it)
            }
        )
    }

    override suspend fun handleEvent(event: NutrientsEvent) {
        when (event) {
            NutrientsEvent.RetryNutrientsRequest -> nutrientsRequest.retry()
            is NutrientsEvent.QuantityChange -> quantityFlow.value = event.quantityChange
            is NutrientsEvent.SelectFood -> {
                selectedFoodFlow.value = event.food
            }
            is NutrientsEvent.SelectSize -> {
                selectedSizeFlow.value = event.size
            }
        }
    }
}