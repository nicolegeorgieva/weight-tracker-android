package com.weighttracker

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.weighttracker.component.BackButton

@Composable
fun AboutScreen() {
    Column() {
        BackButton(screens = Screens.Hello)
        Spacer(modifier = Modifier.height(28.dp))
        Text(text = "About")
    }
}