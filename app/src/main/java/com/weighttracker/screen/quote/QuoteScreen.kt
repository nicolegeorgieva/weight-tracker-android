package com.weighttracker.screen.quote

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
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

        Spacer(modifier = Modifier.height(12.dp))

        InputField(
            value = state.quote ?: "",
            placeholder = "Type custom quote here",
            onValueChange = {
                onEvent(QuoteEvent.QuoteInput(quote = it))
            })

        Spacer(modifier = Modifier.height(32.dp))

        Text(text = "or choose one from the existing list:")

        Spacer(modifier = Modifier.height(12.dp))
    }
}