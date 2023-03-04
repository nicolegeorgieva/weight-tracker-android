package com.weighttracker.screen.nutrients

import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun NutrientScreen() {
    val viewModel: NutrientsViewModel = viewModel()
    val state by viewModel.uiState.collectAsState()
    UI(state = state, onEvent = viewModel::onEvent)
}

@Composable
private fun UI(
    state: NutrientsState,
    onEvent: (NutrientsEvent) -> Unit,
) {
    Row {
        Button(onClick = {
            onEvent(NutrientsEvent.SelectSize(FoodSize.Small))
        }
        ) {
            Text(text = "Small")
        }
    }
}