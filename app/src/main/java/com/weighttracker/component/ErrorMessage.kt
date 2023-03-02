package com.weighttracker.component

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ErrorMessage(onClick: () -> Unit) {
    Text(
        modifier = Modifier.padding(vertical = 24.dp),
        text = "Error!",
        fontSize = 24.sp
    )
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.DarkGray, contentColor = Color.White
        )
    ) {
        Text(text = "Retry")
    }
}