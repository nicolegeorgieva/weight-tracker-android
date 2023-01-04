package com.weighttracker.screen.weightGoal

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.weighttracker.Screens
import com.weighttracker.component.BackButton
import com.weighttracker.component.NumberInputField
import java.text.DecimalFormat

@Composable
fun WeightGoalScreen() {
    val viewModel: WeightGoalViewModel = viewModel()
    val state by viewModel.uiState.collectAsState()

    UI(state = state, onEvent = viewModel::onEvent)
}

@Composable
private fun UI(
    state: WeightGoalState,
    onEvent: (WeightGoalEvent) -> Unit
) {
    Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 16.dp)) {
        BackButton(screens = Screens.BMI)

        Spacer(modifier = Modifier.height(32.dp))

        Text(text = "Current weight: ${state.currentWeight} ${state.weightUnit}", fontSize = 16.sp)

        Spacer(modifier = Modifier.height(16.dp))

        Row(verticalAlignment = Alignment.Bottom) {
            Text(text = "Goal weight: ", fontSize = 16.sp)

            Spacer(modifier = Modifier.width(4.dp))

            NumberInputField(modifier = Modifier
                .width(64.dp)
                .height(48.dp),
                number = state.goalWeight,
                placeholder = "", onValueChange = {
                    onEvent(WeightGoalEvent.WeightGoalInput(targetWeight = it))
                })

            Spacer(modifier = Modifier.width(4.dp))

            Text(text = state.weightUnit)
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "You have to lose ${state.weightToLose} ${state.weightUnit} to achieve your" +
                    " weight goal.", fontSize = 16.sp
        )

        Spacer(modifier = Modifier.height(64.dp))

        Text(
            text = "WEIGHT LOSS PLAN:",
            fontWeight = FontWeight.Bold,
            fontSize = 24.sp
        )

        Spacer(modifier = Modifier.height(16.dp))

        val optimisticMonthsFormatted = state.weightLossPeriod?.let {
            DecimalFormat("###,###.#")
                .format(it.optimisticMonths)
        }

        val realisticMonthsFormatted = state.weightLossPeriod?.let {
            DecimalFormat("###,###.#")
                .format(it.realisticMonths)
        }

        val pessimisticMonthsFormatted = state.weightLossPeriod?.let {
            DecimalFormat("###,###.#")
                .format(it.pessimisticMonths)
        }

        Text(
            text = "optimistic: $optimisticMonthsFormatted months",
            color = Color.Red
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "realistic: $realisticMonthsFormatted months",
            color = Color(0xFF17701B)
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "pessimistic: $pessimisticMonthsFormatted months",
            color = Color(0xFF3C2A2A)
        )
    }
}