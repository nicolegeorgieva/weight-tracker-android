package com.weighttracker.screen.bmi

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
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
        Spacer(modifier = Modifier.height(16.dp))
        NumberInputField(number = state.weight, placeholder = "Weight", onValueChange = {
            onEvent(BmiEvent.WeightChange(newWeightRec = it))
        })
        Spacer(modifier = Modifier.height(16.dp))
        NumberInputField(number = state.height, placeholder = "Height", onValueChange = {
            onEvent(BmiEvent.HeightChange(newHeightRec = it))
        })
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "Your BMI is ${state.bmi}")
    }
}


// region Preview (YOUR PREVIEWS BEGIN HERE)
@Preview
@Composable
private fun Preview() {
    AppTheme {
        UI(state = BmiState(
            weight = 0.0,
            height = 0.0,
            bmi = 0.0
        ), onEvent = {})
    }
}
// endregion