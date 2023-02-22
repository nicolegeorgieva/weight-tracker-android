package com.weighttracker.screen.settings

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
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
import com.weighttracker.navigateTo

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
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp)
    ) {
        item(key = "back button") {
            BackButton(screens = Screens.BMI)

            Spacer(modifier = Modifier.height(28.dp))
        }

        item(key = "weight unit title") {
            Text(text = "Weight unit", fontSize = 16.sp)

            Spacer(modifier = Modifier.height(8.dp))
        }

        item(key = "weight unit buttons") {
            Row() {
                UnitButton(
                    selected = state.kg,
                    onClick = {
                        onEvent(SettingsEvent.KgSelect(kg = true))
                    },
                    text = "kg"
                )

                Spacer(modifier = Modifier.width(8.dp))

                UnitButton(
                    selected = !state.kg,
                    onClick = {
                        onEvent(SettingsEvent.KgSelect(kg = false))
                    },
                    text = "lb"
                )
            }

            Spacer(modifier = Modifier.height(8.dp))
        }

        item(key = "height unit title") {
            Text(text = "Height unit", fontSize = 16.sp)

            Spacer(modifier = Modifier.height(8.dp))
        }

        item(key = "height unit buttons") {
            Row() {
                UnitButton(
                    selected = state.m,
                    onClick = {
                        onEvent(SettingsEvent.MSelect(m = true))
                    },
                    text = "m"
                )

                Spacer(modifier = Modifier.width(8.dp))

                UnitButton(
                    selected = !state.m,
                    onClick = {
                        onEvent(SettingsEvent.MSelect(m = false))
                    },
                    text = "feet"
                )
            }

            Spacer(modifier = Modifier.height(16.dp))
        }

        item(key = "convert units") {
            FeatureButton(
                color = ButtonDefaults.buttonColors(
                    contentColor = Color.White,
                    containerColor = Color.DarkGray
                ),
                screen = Screens.Converter,
                text = "Convert units"
            )

            Spacer(modifier = Modifier.height(12.dp))
        }

        item(key = "add quote") {
            FeatureButton(
                color = ButtonDefaults.buttonColors(
                    contentColor = Color.White,
                    containerColor = Color.Magenta
                ),
                screen = Screens.Quote(backTo = Screens.Settings),
                text = "Add a quote to home screen"
            )

            Spacer(modifier = Modifier.height(12.dp))
        }

        item(key = "weight goal") {
            FeatureButton(
                color = ButtonDefaults.buttonColors(
                    contentColor = Color.White,
                    containerColor = Color(0xFFE91E63)
                ),
                screen = Screens.WeightGoal,
                text = "Weight goal"
            )

            Spacer(modifier = Modifier.height(12.dp))
        }

        item(key = "articles") {
            FeatureButton(
                color = ButtonDefaults.buttonColors(
                    contentColor = Color.White,
                    containerColor = Color(0xFFFF9800)
                ),
                screen = Screens.Articles,
                text = "Articles"
            )

            Spacer(modifier = Modifier.height(12.dp))
        }

        item(key = "weight records") {
            FeatureButton(
                color = ButtonDefaults.buttonColors(
                    contentColor = Color.White,
                    containerColor = Color(0xFF944E62)
                ),
                screen = Screens.WeightRecords(backTo = Screens.Settings),
                text = "Weight records"
            )

            Spacer(modifier = Modifier.height(12.dp))
        }

        item(key = "activity records") {
            FeatureButton(
                color = ButtonDefaults.buttonColors(
                    contentColor = Color.White,
                    containerColor = Color(0xFF2CB432)
                ),
                screen = Screens.ActivityRecords(backTo = Screens.Settings),
                text = "Activity records"
            )

            Spacer(modifier = Modifier.height(12.dp))
        }

        item(key = "water records") {
            FeatureButton(
                color = ButtonDefaults.buttonColors(
                    contentColor = Color.White,
                    containerColor = Color(0xFF2A337A)
                ),
                screen = Screens.WaterRecords(backTo = Screens.Settings),
                text = "Water records"
            )
        }
    }
}

@Composable
fun FeatureButton(color: ButtonColors, screen: Screens, text: String) {
    Button(
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp),
        colors = color,
        shape = RoundedCornerShape(16.dp),
        onClick = {
            navigateTo(screen)
        }
    ) {
        Text(text = text)
    }
}

@Composable
fun UnitButton(selected: Boolean, onClick: () -> Unit, text: String) {
    Button(
        enabled = true,
        colors = if (selected) {
            ButtonDefaults.buttonColors(
                containerColor = Color.Blue,
                contentColor = Color.White
            )
        } else ButtonDefaults.buttonColors(
            containerColor = Color.Gray,
            contentColor = Color.White
        ),
        shape = RoundedCornerShape(24.dp),
        onClick = {
            onClick()
        }) {
        Text(text = text)
    }
}