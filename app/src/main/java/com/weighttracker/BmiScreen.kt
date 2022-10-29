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
        Spacer(modifier = Modifier.height(16.dp))
        MoreInfo()
    }
}

private fun bmi(height: Double, weight: Double): Double {
    return weight / (height * height)
}

@Composable
private fun MoreInfo() {
    var expanded by remember {
        mutableStateOf(false)
    }
    Text(
        text = if (expanded) {
            "Tap to hide"
        } else {
            "Tap for more info"
        },
        modifier = Modifier.clickable {
            expanded = !expanded
        },
        color = Color.LightGray,
        fontWeight = FontWeight.Bold
    )

    if (expanded) {
        Text(
            text = "Body mass index (BMI) is a value derived from the mass (weight) and" +
                    "height of a person. The BMI is defined as the body mass divided by the square" +
                    "of the body height, and is expressed in units of kg/m2, resulting from mass in" +
                    "kilograms and height in metres.",
            modifier = Modifier,
            color = Color.DarkGray
        )
    }
}