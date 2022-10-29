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
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Composable
fun MainScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White)
            .padding(horizontal = 20.dp, vertical = 20.dp)
    ) {
        Greeting()
        Spacer(modifier = Modifier.height(4.dp))
        Quote()
        Spacer(modifier = Modifier.height(4.dp))
        Date()
        Spacer(modifier = Modifier.height(48.dp))
        NavigateToWeightConverter()
    }
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
        color = cyan,
        fontSize = 24.sp,
        fontWeight = FontWeight.Bold,
        fontFamily = FontFamily.Serif,
        text = "Hello, ${currentName}!"
    )
}

@Composable
fun Quote() {
    val currentQuote = remember {
        listOf(
            "Strive to get better every day.",
            "The most valuable investment is that in yourself.",
            "Better. Faster. Stronger."
        ).shuffled().first()
    }
    Text(
        text = currentQuote,
        color = Color.DarkGray,
        fontWeight = FontWeight.Bold,
        fontStyle = FontStyle.Italic,
        fontSize = 16.sp,
    )
}

@Composable
fun Date() {
    val currentDate = LocalDateTime.now()
    Text(
        text = currentDate.format("dd MMM yyyy"),
        color = Color.DarkGray,
        fontSize = 12.sp,
    )
}

private fun LocalDateTime.format(pattern: String): String {
    val formatter = DateTimeFormatter.ofPattern(pattern)
    return formatter.format(this)
}

@Composable
fun NavigateToWeightConverter() {
    Text(text = "Convert units",
        modifier = Modifier.clickable {
            currentScreen.value = Screens.Converter
        }
    )

}

@Preview(showBackground = true)
@Composable
fun Preview() {
    AppTheme {
        MainScreen()
    }
}