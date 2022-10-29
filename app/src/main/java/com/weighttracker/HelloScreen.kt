package com.weighttracker

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.weighttracker.component.BackButton

@Composable
fun HelloScreen() {
    Column() {
        BackButton(screens = Screens.Main)
        Spacer(modifier = Modifier.height(48.dp))
        Text(text = "Hello!")
        Spacer(modifier = Modifier.height(48.dp))
        NavigateToAboutScreen()
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
