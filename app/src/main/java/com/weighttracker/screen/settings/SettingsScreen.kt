package com.weighttracker.screen.settings

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.weighttracker.Screens
import com.weighttracker.component.Header
import com.weighttracker.component.SectionTitle
import com.weighttracker.component.customDivider
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
        item(key = "header") {
            Header(back = Screens.BMI, title = "Settings")

            Spacer(modifier = Modifier.height(28.dp))
        }

        unitsSection(
            kgSelected = state.kg,
            onKgSelect = { kgSelected ->
                onEvent(SettingsEvent.KgSelect(kg = kgSelected))
            },
            mSelected = state.m,
            onMSelect = { mSelected ->
                onEvent(SettingsEvent.MSelect(m = mSelected))
            }
        )

        item(key = "spacer") {
            Spacer(modifier = Modifier.height(32.dp))
        }

        recordsSection()

        item(key = "spacer 2") {
            Spacer(modifier = Modifier.height(32.dp))
        }

        usefulSection()
    }
}

fun LazyListScope.unitsSection(
    kgSelected: Boolean,
    onKgSelect: (Boolean) -> Unit,
    mSelected: Boolean,
    onMSelect: (Boolean) -> Unit
) {
    item(key = "units section title") {
        SectionTitle(text = "Units", color = Color.Gray)

        Spacer(modifier = Modifier.height(8.dp))
    }

    item("weight unit title and buttons") {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(modifier = Modifier.weight(2f), text = "Weight", fontSize = 16.sp)

            UnitButton(
                selected = kgSelected,
                onClick = {
                    onKgSelect(true)
                },
                text = "kg"
            )

            Spacer(modifier = Modifier.width(8.dp))

            UnitButton(
                selected = !kgSelected,
                onClick = {
                    onKgSelect(false)
                },
                text = "lb"
            )
        }

        customDivider()

        Spacer(modifier = Modifier.height(12.dp))
    }

    item(key = "height unit title and buttons") {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(modifier = Modifier.weight(2f), text = "Height", fontSize = 16.sp)

            UnitButton(
                selected = mSelected,
                onClick = {
                    onMSelect(true)
                },
                text = "m"
            )

            Spacer(modifier = Modifier.width(8.dp))

            UnitButton(
                selected = !mSelected,
                onClick = {
                    onMSelect(false)
                },
                text = "feet"
            )
        }

        customDivider()

        Spacer(modifier = Modifier.height(12.dp))
    }

    item(key = "water unit title and buttons") {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(modifier = Modifier.weight(2f), text = "Water", fontSize = 16.sp)

            Button(onClick = { /*TODO*/ }) {
                Text(text = "l")
            }

            Spacer(modifier = Modifier.width(8.dp))

            Button(onClick = { /*TODO*/ }) {
                Text(text = "gallons")
            }
        }

        customDivider()
    }
}

fun LazyListScope.recordsSection() {
    item(key = "records section title") {
        SectionTitle(text = "Records", color = Color.Gray)

        Spacer(modifier = Modifier.height(12.dp))
    }

    item(key = "weight, activity and water records") {
        Row() {
            FeatureButton(
                modifier = Modifier.weight(1f),
                color = ButtonDefaults.buttonColors(
                    contentColor = Color.White,
                    containerColor = Color(0xFF944E62)
                ),
                screen = Screens.WeightRecords(backTo = Screens.Settings),
                text = "Weight"
            )

            Spacer(modifier = Modifier.width(8.dp))

            FeatureButton(
                modifier = Modifier.weight(1f),
                color = ButtonDefaults.buttonColors(
                    contentColor = Color.White,
                    containerColor = Color(0xFF2CB432)
                ),
                screen = Screens.ActivityRecords(backTo = Screens.Settings),
                text = "Activity"
            )

            Spacer(modifier = Modifier.width(8.dp))

            FeatureButton(
                modifier = Modifier.weight(1f),
                color = ButtonDefaults.buttonColors(
                    contentColor = Color.White,
                    containerColor = Color(0xFF2A337A)
                ),
                screen = Screens.WaterRecords(backTo = Screens.Settings),
                text = "Water"
            )
        }
    }
}

fun LazyListScope.usefulSection() {
    item(key = "useful section title") {
        SectionTitle(text = "Useful", color = Color.Gray)

        Spacer(modifier = Modifier.height(12.dp))
    }

    item(key = "convert units") {
        FeatureButton(
            modifier = Modifier.fillMaxWidth(),
            height = 52.dp,
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
            modifier = Modifier.fillMaxWidth(),
            height = 52.dp,
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
            modifier = Modifier.fillMaxWidth(),
            height = 52.dp,
            color = ButtonDefaults.buttonColors(
                contentColor = Color.White,
                containerColor = Color(0xFFC56767)
            ),
            screen = Screens.WeightGoal,
            text = "Weight goal"
        )

        Spacer(modifier = Modifier.height(12.dp))
    }

    item(key = "articles") {
        FeatureButton(
            modifier = Modifier.fillMaxWidth(),
            height = 52.dp,
            color = ButtonDefaults.buttonColors(
                contentColor = Color.White,
                containerColor = Color(0xFFFF9800)
            ),
            screen = Screens.Articles,
            text = "Articles"
        )
    }
}

@Composable
fun FeatureButton(
    color: ButtonColors,
    screen: Screens,
    text: String,
    modifier: Modifier = Modifier,
    height: Dp = 48.dp
) {
    Button(
        modifier = modifier
            .height(height),
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