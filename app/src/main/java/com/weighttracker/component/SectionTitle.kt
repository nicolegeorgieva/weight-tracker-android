package com.weighttracker.component

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

@Composable
fun SectionTitle(text: String, color: Color = Color.Black) {
    Text(
        text = text,
        color = color,
        fontSize = 20.sp,
        fontWeight = FontWeight.Bold
    )
}