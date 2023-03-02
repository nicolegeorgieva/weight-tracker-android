package com.weighttracker.component

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun LoadingMessage() {
    Text(
        modifier = Modifier.padding(24.dp),
        text = "Loading",
        fontSize = 24.sp
    )
    CircularProgressIndicator()
}