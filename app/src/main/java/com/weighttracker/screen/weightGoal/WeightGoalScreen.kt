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
import com.weighttracker.formatNumber

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
                .height(52.dp),
                number = state.goalWeight,
                placeholder = "", onValueChange = {
                    onEvent(WeightGoalEvent.WeightGoalInput(targetWeight = it))
                })

            Spacer(modifier = Modifier.width(4.dp))

            Text(text = state.weightUnit)
        }

        Spacer(modifier = Modifier.height(16.dp))

        val weightToLose = state.weightToLose?.let { formatNumber(it) }

        if (weightToLose != null) {
            Text(
                text = "You have to lose $weightToLose ${state.weightUnit} to achieve your" +
                        " weight goal.", fontSize = 16.sp
            )
            Spacer(modifier = Modifier.height(64.dp))
        }

        if (state.plan != null) {
            Text(
                text = "WEIGHT LOSS PLAN:",
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp
            )

            Spacer(modifier = Modifier.height(16.dp))

            val optimisticMonths = formatNumber(state.plan.optimistic.totalMonths)
            val realisticMonths = formatNumber(state.plan.realistic.totalMonths)
            val pessimisticMonths = formatNumber(state.plan.pessimistic.totalMonths)

            Text(
                text = "optimistic: $optimisticMonths months (${state.plan.optimistic.lossPerMonth}"
                        + " ${state.weightUnit} per month)",
                fontSize = 16.sp,
                color = Color.Red
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "realistic: $realisticMonths months (${state.plan.realistic.lossPerMonth}" +
                        " ${state.weightUnit} per month)",
                fontSize = 16.sp,
                color = Color(0xFF17701B)
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "pessimistic: $pessimisticMonths months (${state.plan.pessimistic.lossPerMonth}" +
                        " ${state.weightUnit} per month)",
                fontSize = 16.sp,
                color = Color(0xFF3C2A2A)
            )
        }
    }
}