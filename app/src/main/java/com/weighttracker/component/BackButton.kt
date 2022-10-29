package com.weighttracker.component

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.clickable
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.weighttracker.Screens
import com.weighttracker.navigateTo

@Composable
fun BackButton(screens: Screens) {
    Text(
        text = "Back",
        modifier = Modifier.clickable {
            navigateTo(screens)
        },
        color = Color.Red
    )
    BackHandler {
        navigateTo(screens)
    }
}