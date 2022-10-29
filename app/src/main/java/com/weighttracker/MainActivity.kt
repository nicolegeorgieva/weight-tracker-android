package com.weighttracker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppTheme {
                when (currentScreen.value) {
                    Screens.Main -> MainScreen()
                    Screens.Converter -> ConverterScreen()
                    Screens.Hello -> HelloScreen()
                    Screens.About -> AboutScreen()
                }
            }
        }
    }
}