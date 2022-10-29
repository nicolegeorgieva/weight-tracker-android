package com.weighttracker

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.weighttracker.component.BackButton
import com.weighttracker.component.NumberInputField

@Composable
fun BmiScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White)
            .padding(horizontal = 36.dp, vertical = 36.dp)
    ) {
        BackButton(screens = Screens.Main)

        Spacer(modifier = Modifier.height(32.dp))

        Text(text = "Height")
        var heightInput by remember { mutableStateOf(1.0) }
        NumberInputField(
            onNumberEnter = {
                if (it != null) {
                    heightInput = it
                }
            }
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(text = "Weight")
        var weightInput by remember { mutableStateOf(0.0) }
        NumberInputField(
            onNumberEnter = {
                if (it != null) {
                    weightInput = it
                }
            }
        )

        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "Your BMI is: ${bmi(height = heightInput, weight = weightInput)}")
    }
}

private fun bmi(height: Double, weight: Double): Double {
    return weight / (height * height)
}