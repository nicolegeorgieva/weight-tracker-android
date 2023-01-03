package com.weighttracker.screen.quote

import androidx.compose.foundation.layout.*
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
import com.weighttracker.component.BackButton
import com.weighttracker.component.InputField

@Composable
fun QuoteScreen() {
    val viewModel: QuoteViewModel = viewModel()
    val state by viewModel.uiState.collectAsState()

    UI(state = state, onEvent = viewModel::onEvent)
}

@Composable
private fun UI(
    state: QuoteState,
    onEvent: (QuoteEvent) -> Unit,
) {
    Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 16.dp)) {
        BackButton(screens = Screens.Settings)

        Spacer(modifier = Modifier.height(32.dp))

        Text(text = "Add custom quote")

        Spacer(modifier = Modifier.height(8.dp))

        Row() {
            InputField(
                value = state.quote ?: "",
                placeholder = "Type custom quote here...",
                onValueChange = {
                    onEvent(QuoteEvent.QuoteInput(quote = it))
                })

            Spacer(modifier = Modifier.weight(1f))

            Button(
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Blue,
                    contentColor = Color.White
                ),
                onClick = {
                    onEvent(QuoteEvent.Clear)
                }
            ) {
                Text(text = "Clear")
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        Text(text = "or choose one from the existing list:")

        Spacer(modifier = Modifier.height(8.dp))

        Button(colors = ButtonDefaults.buttonColors(
            containerColor = Color(0xFF2E8B24),
            contentColor = Color.White
        ),
            onClick = { /*TODO*/ }) {
            Text(text = "Journey of thousand miles starts with one step.")
        }
    }
}