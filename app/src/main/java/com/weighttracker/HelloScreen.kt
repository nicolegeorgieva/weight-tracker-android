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
fun HelloScreen() {
    Column() {
        BackButton()
        Spacer(modifier = Modifier.height(48.dp))
        Text(text = "Hello!")
        Spacer(modifier = Modifier.height(48.dp))
        NavigateToAboutScreen()
    }
}

@Composable
private fun BackButton() {
    Text(text = "Back",
        modifier = Modifier.clickable {
            navigateTo(Screens.Main)
        }
    )
    BackHandler {
        navigateTo(Screens.Main)
    }
}

@Composable
private fun NavigateToAboutScreen() {
    Text(text = "About",
        modifier = Modifier.clickable {
            navigateTo(Screens.About)
        }
    )
}
