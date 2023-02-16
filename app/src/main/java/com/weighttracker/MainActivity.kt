package com.weighttracker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.weighttracker.screen.activityRecords.ActivityRecordsScreen
import com.weighttracker.screen.articles.ArticlesScreen
import com.weighttracker.screen.bmi.BmiScreen
import com.weighttracker.screen.converter.ConverterScreen
import com.weighttracker.screen.quote.QuoteScreen
import com.weighttracker.screen.settings.SettingsScreen
import com.weighttracker.screen.waterRecords.WaterRecordsScreen
import com.weighttracker.screen.weightGoal.WeightGoalScreen
import com.weighttracker.screen.weightRecords.WeightRecordsScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppTheme {
                when (val screen = currentScreen.value) {
                    Screens.BMI -> BmiScreen()
                    Screens.Settings -> SettingsScreen()
                    Screens.Converter -> ConverterScreen()
                    Screens.Quote -> QuoteScreen()
                    Screens.WeightGoal -> WeightGoalScreen()
                    Screens.Articles -> ArticlesScreen()
                    is Screens.WeightRecords -> WeightRecordsScreen(screen = screen)
                    is Screens.ActivityRecords -> ActivityRecordsScreen(screen = screen)
                    is Screens.WaterRecords -> WaterRecordsScreen(screen = screen)
                }
            }
        }
    }
}