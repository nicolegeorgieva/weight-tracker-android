package com.weighttracker.screen.converter

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun ConverterScreen() {
    val viewModel: ConverterViewModel = viewModel()
    val state by viewModel.uiState.collectAsState()

    UI(state = state, onEvent = viewModel::onEvent)
}

@Composable
private fun UI(
    state: ConverterState,
    onEvent: (ConverterEvent) -> Unit,
) {
    Column {
        Text(text = "Hi")
    }
}