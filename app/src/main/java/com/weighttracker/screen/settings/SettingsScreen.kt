package com.weighttracker.screen.settings

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.weighttracker.Screens
import com.weighttracker.component.BackButton

@Composable
fun SettingsScreen() {
    val viewModel: SettingsViewModel = viewModel()
    val state by viewModel.uiState.collectAsState()

    UI(state = state, onEvent = viewModel::onEvent)
}

@Composable
private fun UI(
    state: SettingsState,
    onEvent: (SettingsEvent) -> Unit
) {
    Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 16.dp)) {
        BackButton(screens = Screens.BMI)

        Spacer(modifier = Modifier.height(24.dp))

        Text(text = "Weight unit")

        Spacer(modifier = Modifier.height(16.dp))

        Row() {
            Button(
                enabled = true,
                shape = RoundedCornerShape(32.dp),
                onClick = { /*TODO*/ }) {
                Text(text = "kg")
            }
        }
    }
}