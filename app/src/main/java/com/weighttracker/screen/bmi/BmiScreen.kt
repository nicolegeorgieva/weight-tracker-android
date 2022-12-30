package com.weighttracker.screen.bmi

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.weighttracker.AppTheme
import com.weighttracker.component.NumberInputField

@Composable
fun BmiScreen() {
    val viewModel: BmiViewModel = viewModel()
    val state by viewModel.uiState.collectAsState()

    UI(state = state, onEvent = viewModel::onEvent)
}

@Composable
private fun UI(
    state: BmiState,
    onEvent: (BmiEvent) -> Unit,
) {
    Column {
        NumberInputField(
            number = null,
            placeholder = "Weight",
            onValueChange = {

            }
        )
    }
}


// region Preview (YOUR PREVIEWS BEGIN HERE)
@Preview
@Composable
private fun Preview() {
    AppTheme {
        UI(
            state = BmiState(
                weight = 0.0
            ),
            onEvent = {}
        )
    }
}
// endregion