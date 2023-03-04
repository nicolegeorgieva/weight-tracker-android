package com.weighttracker.screen.nutrients

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.weighttracker.Screens
import com.weighttracker.component.ErrorMessage
import com.weighttracker.component.Header
import com.weighttracker.component.LoadingMessage
import com.weighttracker.network.RemoteCall

@Composable
fun NutrientScreen() {
    val viewModel: NutrientsViewModel = viewModel()
    val state by viewModel.uiState.collectAsState()
    UI(state = state, onEvent = viewModel::onEvent)
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun UI(
    state: NutrientsState,
    onEvent: (NutrientsEvent) -> Unit,
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
    ) {
        item(key = "header") {
            Header(back = Screens.Settings, title = "Nutrients")

            Spacer(modifier = Modifier.height(24.dp))
        }

        item(key = "food section title") {
            Text(text = "Select one of the following foods to get nutritional info about it")
        }

        item(key = "foods grid") {
            FlowRow(
                modifier = Modifier.fillMaxWidth(),
            ) {
                state.foods.forEach { it ->
                    FoodItem(
                        selected = it == state.selectedFood,
                        foodInput = it
                    ) {
                        onEvent(NutrientsEvent.SelectFood(it))
                    }
                }
            }
        }

        item(key = "food size") {
            FoodSizeComponent(
                selectedSize = state.selectedSize,
                onClick = { foodSize ->
                    onEvent(NutrientsEvent.SelectSize(foodSize))
                }
            )
        }

        item(key = "request info") {
            when (state.nutrientsRequest) {
                is RemoteCall.Error -> ErrorMessage {
                    onEvent(NutrientsEvent.RetryNutrientsRequest)
                }
                RemoteCall.Loading -> LoadingMessage()
                is RemoteCall.Ok -> {
                    Text(text = state.nutrientsRequest.data.toString())
                }
                null -> {}
            }
        }
    }
}

@Composable
fun FoodItem(
    selected: Boolean,
    foodInput: String,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        colors = if (selected) {
            ButtonDefaults.buttonColors(
                containerColor = Color.Green,
                contentColor = Color.White
            )
        } else {
            ButtonDefaults.buttonColors(
                containerColor = Color.Gray,
                contentColor = Color.White
            )
        }
    ) {
        Text(text = foodInput)
    }
}

@Composable
fun FoodSizeComponent(
    selectedSize: FoodSize,
    onClick: (FoodSize) -> Unit
) {
    Text(text = "Select size")

    Spacer(modifier = Modifier.height(8.dp))

    Row {
        Button(
            colors = if (selectedSize == FoodSize.Small) {
                ButtonDefaults.buttonColors(
                    containerColor = Color.Blue,
                    contentColor = Color.White
                )
            } else {
                ButtonDefaults.buttonColors(
                    containerColor = Color.Gray,
                    contentColor = Color.White
                )
            },
            onClick = {
                onClick(FoodSize.Small)
            }) {
            Text(text = "Small")
        }

        Spacer(modifier = Modifier.width(8.dp))

        Button(colors = if (selectedSize == FoodSize.Medium) {
            ButtonDefaults.buttonColors(
                containerColor = Color.Blue,
                contentColor = Color.White
            )
        } else {
            ButtonDefaults.buttonColors(
                containerColor = Color.Gray,
                contentColor = Color.White
            )
        },
            onClick = {
                onClick(FoodSize.Medium)
            }) {
            Text(text = "Medium")
        }

        Spacer(modifier = Modifier.width(8.dp))

        Button(colors = if (selectedSize == FoodSize.Large) {
            ButtonDefaults.buttonColors(
                containerColor = Color.Blue,
                contentColor = Color.White
            )
        } else {
            ButtonDefaults.buttonColors(
                containerColor = Color.Gray,
                contentColor = Color.White
            )
        },
            onClick = {
                onClick(FoodSize.Large)
            }) {
            Text(text = "Large")
        }
    }
}