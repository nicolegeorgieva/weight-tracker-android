package com.weighttracker.component

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.weighttracker.AppTheme
import com.weighttracker.Screens

@Composable
fun Header(back: Screens, title: String) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        BackButton(screens = back)
        Spacer(modifier = Modifier.width(16.dp))
        Text(
            text = title,
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@Preview
@Composable
private fun Preview() {
    AppTheme {
        Header(back = Screens.BMI, title = "Settings")
    }
}