package com.weighttracker.screen.converter

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.weighttracker.Screens
import com.weighttracker.component.BackButton
import com.weighttracker.component.NumberInputField

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
    Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 16.dp)) {
        BackButton(screens = Screens.Settings)

        Spacer(modifier = Modifier.height(32.dp))

        Row(verticalAlignment = Alignment.Bottom) {
            NumberInputField(modifier = Modifier.weight(1f),
                number = state.lb, placeholder = "", onValueChange = {
                    onEvent(ConverterEvent.LbInput(lb = it))
                })

            Spacer(modifier = Modifier.width(8.dp))

            Text(text = "lb", fontWeight = FontWeight.Bold)

            Spacer(modifier = Modifier.width(16.dp))

            Text(text = "<--->")

            Spacer(modifier = Modifier.width(16.dp))

            NumberInputField(modifier = Modifier.weight(1f),
                number = state.kg, placeholder = "", onValueChange = {
                    onEvent(ConverterEvent.KgInput(kg = it))
                })

            Spacer(modifier = Modifier.width(8.dp))

            Text(text = "kg", fontWeight = FontWeight.Bold)
        }

        Spacer(modifier = Modifier.height(32.dp))

        Row(verticalAlignment = Alignment.Bottom) {
            NumberInputField(modifier = Modifier.weight(1f),
                number = state.feet, placeholder = "", onValueChange = {
                    onEvent(ConverterEvent.FeetInput(feet = it))
                })

            Spacer(modifier = Modifier.width(8.dp))

            Text(text = "ft", fontWeight = FontWeight.Bold)

            Spacer(modifier = Modifier.width(16.dp))

            Text(text = "<--->")

            Spacer(modifier = Modifier.width(16.dp))

            NumberInputField(modifier = Modifier.weight(1f),
                number = state.m, placeholder = "", onValueChange = {
                    onEvent(ConverterEvent.MInput(m = it))
                })

            Spacer(modifier = Modifier.width(8.dp))

            Text(text = "m", fontWeight = FontWeight.Bold)
        }
    }
}