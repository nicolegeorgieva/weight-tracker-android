package com.weighttracker.component

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun ErrorMessage(onClick: () -> Unit) {
    Text(text = "Error!")
    Button(onClick = onClick) {
        Text(text = "Retry")
    }
}