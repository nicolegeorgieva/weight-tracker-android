package com.weighttracker.screen.nutrients

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.weighttracker.AppTheme
import com.weighttracker.Screens
import com.weighttracker.component.ErrorMessage
import com.weighttracker.component.Header
import com.weighttracker.component.LoadingMessage
import com.weighttracker.network.NetworkError
import com.weighttracker.network.RemoteCall
import com.weighttracker.network.calories.MacroNutrient
import com.weighttracker.network.calories.Nutrients
import com.weighttracker.network.calories.NutrientsResponse

@Composable
fun NutrientScreen() {
    val viewModel: NutrientsViewModel = viewModel()
    val state by viewModel.uiState.collectAsState()
    UI(state = state, onEvent = viewModel::onEvent)
}

@OptIn(ExperimentalLayoutApi::class, ExperimentalMaterial3Api::class)
@Composable
private fun UI(
    state: NutrientsState,
    onEvent: (NutrientsEvent) -> Unit,
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
    ) {
        item(key = "header") {
            Header(
                modifier = Modifier.padding(16.dp),
                back = Screens.Settings,
                title = "Nutrients"
            )

            Spacer(modifier = Modifier.height(16.dp))
        }

        item(key = "food section title") {
            Text(
                modifier = Modifier.padding(horizontal = 16.dp),
                text = "Select one of the following foods to get nutritional info about it"
            )
            Spacer(modifier = Modifier.height(8.dp))
        }

        item(key = "foods grid") {
            FlowRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 14.dp),
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

            Spacer(modifier = Modifier.height(24.dp))
        }

        item(key = "food size") {
            FoodSizeComponent(
                selectedSize = state.selectedSize,
                onClick = { foodSize ->
                    onEvent(NutrientsEvent.SelectSize(foodSize))
                }
            )

            Spacer(modifier = Modifier.height(24.dp))
        }

        item(key = "request info") {
            when (state.nutrientsRequest) {
                is RemoteCall.Error -> ErrorMessage(modifier = Modifier.padding(horizontal = 16.dp)) {
                    onEvent(NutrientsEvent.RetryNutrientsRequest)
                }
                RemoteCall.Loading -> LoadingMessage()
                is RemoteCall.Ok -> {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp),
                        shape = RoundedCornerShape(12.dp),
                        elevation = CardDefaults.cardElevation(48.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = Color(0xFF0091EA),
                            contentColor = Color(0xFFFFFFFF)
                        )
                    ) {
                        Spacer(modifier = Modifier.height(24.dp))

                        Text(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 12.dp),
                            text = "N U T R I T I O N A L  I N F O",
                            textAlign = TextAlign.Center,
                            fontWeight = FontWeight.Bold
                        )

                        Spacer(modifier = Modifier.height(24.dp))

                        Column(
                            modifier = Modifier.padding(horizontal = 12.dp)
                        ) {
                            Row() {
                                Text(
                                    text = "Total calories: ",
                                    fontWeight = FontWeight.Bold
                                )

                                Text(text = "${state.nutrientsRequest.data.calories} kcal")
                            }

                            Spacer(modifier = Modifier.height(8.dp))

                            Row() {
                                Text(
                                    text = "Total weight: ",
                                    fontWeight = FontWeight.Bold
                                )

                                Text(text = "${state.nutrientsRequest.data.totalWeight} g")
                            }

                            Spacer(modifier = Modifier.height(8.dp))

                            Row() {
                                Text(
                                    text = "Carbs: ",
                                    fontWeight = FontWeight.Bold
                                )

                                Text(
                                    text = "${state.nutrientsRequest.data.nutrients.carbs.quantity} " +
                                            state.nutrientsRequest.data.nutrients.carbs.unit
                                )
                            }

                            Spacer(modifier = Modifier.height(8.dp))

                            Row() {
                                Text(
                                    text = "Fiber: ",
                                    fontWeight = FontWeight.Bold
                                )

                                Text(
                                    text = "${state.nutrientsRequest.data.nutrients.fiber.quantity} " +
                                            state.nutrientsRequest.data.nutrients.fiber.unit
                                )
                            }

                            Spacer(modifier = Modifier.height(8.dp))

                            Row() {
                                Text(
                                    text = "Fat: ",
                                    fontWeight = FontWeight.Bold

                                )

                                Text(
                                    text = "${state.nutrientsRequest.data.nutrients.fat.quantity} " +
                                            state.nutrientsRequest.data.nutrients.fat.unit
                                )
                            }

                            Spacer(modifier = Modifier.height(8.dp))

                            Row() {
                                Text(
                                    text = "Protein: ",
                                    fontWeight = FontWeight.Bold

                                )

                                Text(
                                    text = "${state.nutrientsRequest.data.nutrients.protein.quantity} " +
                                            state.nutrientsRequest.data.nutrients.protein.unit
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(24.dp))
                    }
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
        modifier = Modifier.padding(horizontal = 2.dp),
        onClick = onClick,
        colors = if (selected) {
            ButtonDefaults.buttonColors(
                containerColor = Color(0xFFFF6D00),
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
    Text(modifier = Modifier.padding(horizontal = 16.dp), text = "Select size")

    Spacer(modifier = Modifier.height(8.dp))

    Row(modifier = Modifier.padding(horizontal = 16.dp)) {
        Button(
            modifier = Modifier.weight(1f),
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

        Button(
            modifier = Modifier.weight(1f),
            colors = if (selectedSize == FoodSize.Medium) {
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

        Button(
            modifier = Modifier.weight(1f),
            colors = if (selectedSize == FoodSize.Large) {
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

@Preview
@Composable
private fun Preview() {
    AppTheme {
        UI(state = NutrientsState(
            quantity = 1,
            foods = listOf(
                "rice", "bread", "chicken", "pizza", "apple", "milk", "biscuit",
                "egg", "banana", "joghurt", "potato", "pork", "turkey", "beef"
            ),
            selectedFood = "rice",
            selectedSize = FoodSize.Medium,
            nutrientsRequest = RemoteCall.Ok(
                NutrientsResponse(
                    calories = 190,
                    nutrients = Nutrients(
                        fat = MacroNutrient(20.0, "g"),
                        protein = MacroNutrient(15.0, "g"),
                        carbs = MacroNutrient(40.0, "g"),
                        fiber = MacroNutrient(5.0, "g")
                    ),
                    totalWeight = 300
                )
            )
        ), onEvent = {})
    }
}

@Preview
@Composable
private fun PreviewLoading() {
    AppTheme {
        UI(state = NutrientsState(
            quantity = 1,
            foods = listOf(
                "rice", "bread", "chicken", "pizza", "apple", "milk", "biscuit",
                "egg", "banana", "joghurt", "potato", "pork", "turkey", "beef"
            ),
            selectedFood = "rice",
            selectedSize = FoodSize.Medium,
            nutrientsRequest = RemoteCall.Loading
        ), onEvent = {})
    }
}

@Preview
@Composable
private fun PreviewError() {
    AppTheme {
        UI(state = NutrientsState(
            quantity = 1,
            foods = listOf(
                "rice", "bread", "chicken", "pizza", "apple", "milk", "biscuit",
                "egg", "banana", "joghurt", "potato", "pork", "turkey", "beef"
            ),
            selectedFood = "rice",
            selectedSize = FoodSize.Medium,
            nutrientsRequest = RemoteCall.Error(NetworkError.Generic)
        ), onEvent = {})
    }
}
