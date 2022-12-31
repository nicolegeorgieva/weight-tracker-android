package com.weighttracker.screen.bmi

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.weighttracker.AppTheme
import com.weighttracker.Screens
import com.weighttracker.component.NumberInputField
import com.weighttracker.navigateTo
import java.text.DecimalFormat

@Composable
fun BmiScreen() {
    val viewModel: BmiViewModel = viewModel()
    val state by viewModel.uiState.collectAsState()

    UI(state = state, onEvent = viewModel::onEvent)
}

@Composable
private fun UI(
    state: BmiState,
    onEvent: (BmiEvent) -> Unit,
) {
    Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 82.dp)) {
        MoreMenuButton()
        Text(
            text = "Log your weight and height",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(16.dp))

        Row(verticalAlignment = Alignment.CenterVertically) {
            NumberInputField(number = state.weight, placeholder = "Weight", onValueChange = {
                onEvent(BmiEvent.WeightChange(newWeightRec = it))
            })
            Spacer(modifier = Modifier.width(8.dp))
            Text(fontWeight = FontWeight.Bold, text = "kg")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row(verticalAlignment = Alignment.CenterVertically) {
            NumberInputField(number = state.height, placeholder = "Height", onValueChange = {
                onEvent(BmiEvent.HeightChange(newHeightRec = it))
            })
            Spacer(modifier = Modifier.width(8.dp))
            Text(fontWeight = FontWeight.Bold, text = "m")
        }

        Spacer(modifier = Modifier.height(16.dp))

        val bmiFormatted =
            if (state.bmi != null) DecimalFormat("###,###.#").format(state.bmi) else ""
        Text(text = "Your BMI is $bmiFormatted.")
    }
}

@Composable
fun MoreMenuButton() {
    IconButton(modifier = Modifier, onClick = {
        navigateTo(screens = Screens.Settings)
    }, enabled = true) {
        Icon(imageVector = Icons.Default.Menu, contentDescription = "more")
    }
}


// region Preview (YOUR PREVIEWS BEGIN HERE)
@Preview
@Composable
private fun Preview() {
    AppTheme {
        UI(
            state = BmiState(
                weight = 0.0, height = 0.0, bmi = 0.0
            ), onEvent = {})
    }
}
// endregion