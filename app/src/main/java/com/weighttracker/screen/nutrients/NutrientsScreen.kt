package com.weighttracker.screen.nutrients

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.weighttracker.Screens
import com.weighttracker.component.Header

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
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
    ) {
        item(key = "header") {
            Header(back = Screens.Settings, title = "Nutrients")
        }

        item(key = "food size") {
            FoodSizeComponent { selectedSize ->
                onEvent(NutrientsEvent.SelectSize(selectedSize))
            }
        }
    }
}

@Composable
fun FoodSizeComponent(onClick: (FoodSize) -> Unit) {
    Row {
        Button(onClick = {
            onClick(FoodSize.Small)
        }) {
            Text(text = "Small")
        }

        Divider(modifier = Modifier.width(8.dp))

        Button(onClick = {
            onClick(FoodSize.Medium)
        }) {
            Text(text = "Medium")
        }

        Divider(modifier = Modifier.width(8.dp))

        Button(onClick = {
            onClick(FoodSize.Large)
        }) {
            Text(text = "Large")
        }
    }
}