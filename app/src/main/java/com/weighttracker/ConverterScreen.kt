package com.weighttracker

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.weighttracker.component.BackButton
import com.weighttracker.component.NumberInputField

@Composable
fun ConverterScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White)
            .padding(horizontal = 36.dp, vertical = 36.dp)
    ) {
        BackButton(screens = Screens.Main)
        Spacer(modifier = Modifier.height(24.dp))
        HeightConverter()
        Spacer(modifier = Modifier.height(24.dp))
        WeightConverter()
    }
}

@Composable
fun HeightConverter() {
    Text(text = "Height in m")
    var heightConverterInput by remember { mutableStateOf(1.0) }
    NumberInputField(
        onNumberEnter = {
            if (it != null) {
                heightConverterInput = it
            }
        }
    )

    Spacer(modifier = Modifier.height(16.dp))

    Text(
        text = "Your height in feet is ${heightConverter(height = heightConverterInput)} ft",
    )
}

private fun heightConverter(height: Double): Double {
    return height * 3.28
}

@Composable
fun WeightConverter() {
    Text(text = "Weight in kg")
    var weightConverterInput by remember { mutableStateOf(0.0) }
    NumberInputField(
        onNumberEnter = {
            if (it != null) {
                weightConverterInput = it
            }
        }
    )

    Spacer(modifier = Modifier.height(16.dp))

    Text(
        text = "Your weight in pounds is: ${weightConverter(weight = weightConverterInput)} lb",
        color = if (weightConverterInput >= 100) {
            Color.Red
        } else {
            Color.Black
        },
        fontWeight = if (weightConverterInput >= 50) {
            FontWeight.Bold
        } else {
            FontWeight.Normal
        }
    )
}

private fun weightConverter(weight: Double): Double {
    return weight * 2.2
}
