package com.weighttracker.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun CustomDivider() {
    Divider(
        modifier = Modifier.fillMaxWidth(),
        color = Color.LightGray,
        thickness = 2.dp
    )
}