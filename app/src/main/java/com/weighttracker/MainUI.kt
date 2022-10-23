package com.weighttracker

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.*
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
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.Blue)
            .padding(horizontal = 20.dp, vertical = 20.dp)
    ) {
        Greeting()
        Spacer(modifier = Modifier.height(5.dp))
        Quote()
    }
}

@Composable
fun Quote() {
    val quotes = listOf(
        "Strive to get better every day.",
        "The most valuable investment is in yourself.",
        "Better. Faster. Stronger."
    ).shuffled()
    val currentQuote by remember {
        mutableStateOf(quotes.first())
    }
    Text(
        text = currentQuote,
        color = Color.White,
        fontStyle = FontStyle.Italic
    )
}

@Composable
fun Greeting() {
    val names = listOf("Iliyan", "Nicole", "Rose", "Garen", "Caitlin")
        .shuffled()
    var currentName by remember {
        mutableStateOf(names.first())
    }
    Text(
        modifier = Modifier.clickable {
            currentName = names.shuffled().first()
        },
        color = Color.White,
        fontSize = 20.sp,
        fontWeight = FontWeight.Bold,
        fontFamily = FontFamily.Serif,
        text = "Hello, ${currentName}!"
    )
}

@Preview(showBackground = true)
@Composable
fun Preview() {
    WeightUI {
        UI()
    }
}