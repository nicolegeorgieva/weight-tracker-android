package com.weighttracker.screen.settings

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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

        Spacer(modifier = Modifier.height(32.dp))

        Text(text = "Weight unit", fontSize = 16.sp)

        Spacer(modifier = Modifier.height(16.dp))

        Row() {
            Button(
                enabled = true,
                colors = if (state.kg) {
                    ButtonDefaults.buttonColors(
                        containerColor = Color.Blue,
                        contentColor = Color.White
                    )
                } else ButtonDefaults.buttonColors(
                    containerColor = Color.Gray,
                    contentColor = Color.White
                ),
                shape = RoundedCornerShape(32.dp),
                onClick = {
                    onEvent(SettingsEvent.KgSelect(kg = true))
                }) {
                Text(text = "kg")
            }
            Spacer(modifier = Modifier.width(8.dp))
            Button(
                enabled = true,
                colors = if (state.kg) {
                    ButtonDefaults.buttonColors(
                        containerColor = Color.Gray,
                        contentColor = Color.White
                    )
                } else ButtonDefaults.buttonColors(
                    containerColor = Color.Blue,
                    contentColor = Color.White
                ),
                shape = RoundedCornerShape(32.dp),
                onClick = {
                    onEvent(SettingsEvent.KgSelect(kg = false))
                }) {
                Text(text = "lb")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(text = "Height unit", fontSize = 16.sp)

        Spacer(modifier = Modifier.height(16.dp))

        Row() {
            Button(
                enabled = true,
                colors = if (state.m) {
                    ButtonDefaults.buttonColors(
                        containerColor = Color.Blue,
                        contentColor = Color.White
                    )
                } else ButtonDefaults.buttonColors(
                    containerColor = Color.Gray,
                    contentColor = Color.White
                ),
                shape = RoundedCornerShape(32.dp),
                onClick = {
                    onEvent(SettingsEvent.MSelect(m = true))
                }
            ) {
                Text(text = "m")
            }
            Spacer(modifier = Modifier.width(8.dp))
            Button(
                enabled = true,
                colors = if (state.m) {
                    ButtonDefaults.buttonColors(
                        containerColor = Color.Gray,
                        contentColor = Color.White
                    )
                } else ButtonDefaults.buttonColors(
                    containerColor = Color.Blue,
                    contentColor = Color.White
                ),
                shape = RoundedCornerShape(32.dp),
                onClick = {
                    onEvent(SettingsEvent.MSelect(m = false))
                }
            ) {
                Text(text = "feet")
            }
        }
    }
}