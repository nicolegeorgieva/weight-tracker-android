package com.weighttracker

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun AboutScreen() {
    Column() {
        BackButton()
        Spacer(modifier = Modifier.height(28.dp))
        Text(text = "About")
    }
}

@Composable
private fun BackButton() {
    Text(
        text = "Back",
        modifier = Modifier.clickable {
            navigateTo(Screens.Hello)
        }
    )
    BackHandler {
        navigateTo(Screens.Hello)
    }
}