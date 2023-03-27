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
import com.weighttracker.component.CustomDivider
import com.weighttracker.component.Header
import com.weighttracker.component.SectionTitle
import com.weighttracker.domain.data.HeightUnit
import com.weighttracker.domain.data.WaterUnit
import com.weighttracker.domain.data.WeightUnit
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
            weightUnit = state.weightUnit,
            onWeightUnitSelect = { weightUnit ->
                onEvent(SettingsEvent.ChangeWeightUnit(weightUnit = weightUnit))
            },
            heightUnit = state.heightUnit,
            onHeightUnitSelect = { heightUnit ->
                onEvent(SettingsEvent.ChangeHeightUnit(heightUnit = heightUnit))
            },
            waterUnit = state.waterUnit,
            onWaterUnitSelect = { waterUnit ->
                onEvent(SettingsEvent.ChangeWaterUnit(waterUnit = waterUnit))

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
    weightUnit: WeightUnit,
    onWeightUnitSelect: (WeightUnit) -> Unit,
    heightUnit: HeightUnit,
    onHeightUnitSelect: (HeightUnit) -> Unit,
    waterUnit: WaterUnit,
    onWaterUnitSelect: (WaterUnit) -> Unit
) {
    item(key = "units section title") {
        SectionTitle(text = "Units", color = Color.Gray)

        Spacer(modifier = Modifier.height(8.dp))
    }

    item("weight unit title and buttons") {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(modifier = Modifier.weight(2f), text = "Weight", fontSize = 16.sp)

            UnitButton(
                selected = when (weightUnit) {
                    WeightUnit.Kg -> true
                    WeightUnit.Lb -> false
                },
                onClick = {
                    onWeightUnitSelect(WeightUnit.Kg)
                },
                text = "kg"
            )

            Spacer(modifier = Modifier.width(8.dp))

            UnitButton(
                selected = when (weightUnit) {
                    WeightUnit.Kg -> false
                    WeightUnit.Lb -> true
                },
                onClick = {
                    onWeightUnitSelect(WeightUnit.Lb)
                },
                text = "lb"
            )
        }

        CustomDivider()

        Spacer(modifier = Modifier.height(12.dp))
    }

    item(key = "height unit title and buttons") {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(modifier = Modifier.weight(2f), text = "Height", fontSize = 16.sp)

            UnitButton(
                selected = when (heightUnit) {
                    HeightUnit.M -> true
                    HeightUnit.Ft -> false
                },
                onClick = {
                    onHeightUnitSelect(HeightUnit.M)
                },
                text = "m"
            )

            Spacer(modifier = Modifier.width(8.dp))

            UnitButton(
                selected = when (heightUnit) {
                    HeightUnit.M -> false
                    HeightUnit.Ft -> true
                },
                onClick = {
                    onHeightUnitSelect(HeightUnit.Ft)
                },
                text = "ft"
            )
        }

        CustomDivider()

        Spacer(modifier = Modifier.height(12.dp))
    }

    item(key = "water unit title and buttons") {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(modifier = Modifier.weight(2f), text = "Water", fontSize = 16.sp)

            UnitButton(
                selected = when (waterUnit) {
                    WaterUnit.L -> true
                    WaterUnit.Gal -> false
                },
                onClick = {
                    onWaterUnitSelect(WaterUnit.L)
                },
                text = "l"
            )

            Spacer(modifier = Modifier.width(8.dp))

            UnitButton(
                selected = when (waterUnit) {
                    WaterUnit.L -> false
                    WaterUnit.Gal -> true
                },
                onClick = {
                    onWaterUnitSelect(WaterUnit.Gal)
                },
                text = "gal"
            )
        }

        CustomDivider()
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
                    containerColor = Color(0xFFE21E45)
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
                    containerColor = Color(0xFF0E3C5F)
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
                containerColor = Color(0xFFFF5722)
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

        Spacer(modifier = Modifier.height(12.dp))
    }

    item(key = "nutrients") {
        FeatureButton(
            modifier = Modifier.fillMaxWidth(),
            height = 52.dp,
            color = ButtonDefaults.buttonColors(
                contentColor = Color.White,
                containerColor = Color(0xFF1A8ECC)
            ),
            screen = Screens.Nutrients,
            text = "Nutrients"
        )

        Spacer(modifier = Modifier.height(12.dp))
    }

    item(key = "recipes") {
        FeatureButton(
            modifier = Modifier.fillMaxWidth(),
            height = 52.dp,
            color = ButtonDefaults.buttonColors(
                contentColor = Color.White,
                containerColor = Color(0xFF1ACCB1)
            ),
            screen = Screens.Recipe,
            text = "Recipes"
        )

        Spacer(modifier = Modifier.height(12.dp))
    }

    item(key = "exercises") {
        FeatureButton(
            modifier = Modifier.fillMaxWidth(),
            height = 52.dp,
            color = ButtonDefaults.buttonColors(
                contentColor = Color.White,
                containerColor = Color(0xFF033E44)
            ),
            screen = Screens.Exercise,
            text = "Exercises"
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