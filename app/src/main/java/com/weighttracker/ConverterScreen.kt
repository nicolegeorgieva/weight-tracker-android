package com.weighttracker

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
    var expandedHeight by remember {
        mutableStateOf(false)
    }
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
        modifier = Modifier.clickable {
            expandedHeight = !expandedHeight
        }
    )
    if (expandedHeight) {
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Height is measure of vertical distance, either vertical extent (how \"tall\"" +
                    "something or someone is) or vertical position (how \"high\" a point is)." +
                    "For example, \"The height of that building is 50 m\" or \"The height of an" +
                    "airplane in-flight is about 10,000 m\". For example, \"Christopher Columbus is" +
                    "5 foot 2 inches in vertical height.\"",
            modifier = Modifier,
            color = Color.DarkGray
        )
    }
}

private fun heightConverter(height: Double): Double {
    return height * 3.28
}

@Composable
fun WeightConverter() {
    var expandedWeight by remember {
        mutableStateOf(false)
    }
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
        },
        modifier = Modifier.clickable {
            expandedWeight = !expandedWeight
        }
    )

    if (expandedWeight) {
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "The SI base unit of mass is the kilogram (kg). In physics, mass is not the" +
                    "same as weight, even though mass is often determined by measuring the object's" +
                    "weight using a spring scale, rather than balance scale comparing it directly" +
                    "with known masses.",
            modifier = Modifier,
            color = Color.DarkGray
        )
    }
}

private fun weightConverter(weight: Double): Double {
    return weight * 2.2
}
