package com.weighttracker.component

import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun LoadingMessage() {
    Text(text = "Loading")
    CircularProgressIndicator()
}