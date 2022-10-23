package com.weighttracker

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun UI() {
    Column(
        Modifier
            .background(color = Color.Blue)
            .padding(horizontal = 20.dp, vertical = 20.dp)
            .fillMaxSize()
    ) {
        Greeting("User")
        Spacer(modifier = Modifier.height(10.dp))
        Greeting("Iliyan")
        Spacer(modifier = Modifier.height(10.dp))
        Greeting("Nicole")
    }
}

@Composable
fun Greeting(name: String) {
    Text(
        color = Color.White,
        fontSize = 20.sp,
        fontWeight = FontWeight.Bold,
        fontStyle = FontStyle.Italic,
        fontFamily = FontFamily.Serif,
        text = "Hello, $name!"
    )
}

@Preview(showBackground = true)
@Composable
fun Preview() {
    WeightUI {
        UI()
    }
}