package com.weighttracker.screen.weightGoal

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.weighttracker.Screens
import com.weighttracker.component.ExpandCollapseArrow
import com.weighttracker.component.Header
import com.weighttracker.component.NumberInputField
import com.weighttracker.component.customDivider
import com.weighttracker.formatNumber
import com.weighttracker.navigateTo

@Composable
fun WeightGoalScreen() {
    val viewModel: WeightGoalViewModel = viewModel()
    val state by viewModel.uiState.collectAsState()

    UI(state = state, onEvent = viewModel::onEvent)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun UI(
    state: WeightGoalState,
    onEvent: (WeightGoalEvent) -> Unit
) {
    LazyColumn(modifier = Modifier.padding(horizontal = 16.dp, vertical = 16.dp)) {
        item(key = "header") {
            Header(back = Screens.Settings, title = "Weight goal")

            Spacer(modifier = Modifier.height(32.dp))
        }

        item(key = "current weight title and value") {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(modifier = Modifier.weight(2f), text = "Current weight")

                Text(
                    modifier = Modifier.clickable {
                        navigateTo(Screens.BMI)
                    },
                    text = "${state.currentWeight} ${state.weightUnit}",
                    fontSize = 16.sp
                )
            }

            customDivider()

            Spacer(modifier = Modifier.height(16.dp))
        }

        item(key = "goal weight title and value") {
            Row(verticalAlignment = Alignment.Bottom) {
                Text(modifier = Modifier.weight(2f), text = "Goal weight", fontSize = 16.sp)

                NumberInputField(modifier = Modifier
                    .width(72.dp)
                    .height(52.dp),
                    number = state.goalWeight,
                    placeholder = "", onValueChange = {
                        onEvent(WeightGoalEvent.WeightGoalInput(targetWeight = it))
                    })

                Spacer(modifier = Modifier.width(4.dp))

                Text(text = state.weightUnit)
            }

            customDivider()

            Spacer(modifier = Modifier.height(24.dp))
        }

        item(key = "message with weight loss value to goal weight") {
            val weightToLose = state.weightToLose?.let { formatNumber(it) }

            if (weightToLose != null) {
                Text(
                    text = "You have to lose $weightToLose ${state.weightUnit} to achieve your" +
                            " weight goal.",
                    fontSize = 16.sp,
                    fontStyle = FontStyle.Italic,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(32.dp))
            }
        }

        item(key = "ideal weight info and button with its value") {
            var expandedState by remember {
                mutableStateOf(false)
            }

            Row(verticalAlignment = Alignment.CenterVertically) {
                Row(modifier = Modifier.weight(2f)) {
                    ExpandCollapseArrow(
                        expanded = expandedState,
                        onExpandChange = { expanded ->
                            expandedState = expanded
                        }
                    )

                    Text(
                        modifier = Modifier.clickable {
                            expandedState = !expandedState
                        },
                        text = "Your ideal weight would be:", fontSize = 16.sp,
                        textDecoration = TextDecoration.Underline
                    )
                }

                Spacer(modifier = Modifier.width(8.dp))

                Row() {
                    val idealWeightFormatted = state.idealWeight?.let { formatNumber(it) }

                    Button(
                        onClick = {
                            onEvent(WeightGoalEvent.WeightGoalInput(targetWeight = state.idealWeight))
                        },
                        enabled = true,
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFC56767))
                    ) {
                        Text(text = "$idealWeightFormatted ${state.weightUnit}", fontSize = 16.sp)
                    }
                }
            }

            if (expandedState) {
                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Your ideal weight is the arithmetic mean between your" +
                            " minimal and maximum normal weight.",
                    color = Color.Black,
                    fontSize = 16.sp
                )
            }

            Spacer(modifier = Modifier.height(48.dp))
        }

        item(key = "weight loss plan card") {
            if (state.plan != null) {
                WeightLossPlanCard(
                    plan = state.plan,
                    weightUnit = state.weightUnit
                )

                Spacer(modifier = Modifier.height(48.dp))
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WeightLossPlanCard(plan: WeightLossPlan?, weightUnit: String) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(Color(color = 0xFFFFE5EB)),
        elevation = CardDefaults.cardElevation(8.dp)

    ) {
        Text(
            modifier = Modifier.padding(16.dp),
            text = "WEIGHT LOSS PLAN:",
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,
            color = Color.Gray
        )

        Spacer(modifier = Modifier.height(8.dp))

        val optimisticMonths = plan?.optimistic?.let { formatNumber(it.totalMonths) }
        val realisticMonths = plan?.realistic?.let { formatNumber(it.totalMonths) }
        val pessimisticMonths = plan?.pessimistic?.let { formatNumber(it.totalMonths) }

        Row() {
            Icon(
                modifier = Modifier
                    .padding(8.dp)
                    .weight(1f),
                imageVector = Icons.Default.Check,
                tint = Color(0xFFF44336),
                contentDescription = ""
            )

            Icon(
                modifier = Modifier
                    .padding(8.dp)
                    .weight(1f),
                imageVector = Icons.Default.Check,
                tint = Color(0xFF3ECE44),
                contentDescription = ""
            )

            Icon(
                modifier = Modifier
                    .padding(8.dp)
                    .weight(1f),
                imageVector = Icons.Default.Check,
                tint = Color(0xFF575757),
                contentDescription = ""
            )
        }

        Row() {
            Text(
                modifier = Modifier
                    .padding(12.dp)
                    .weight(1f),
                text = "OPTIMISTIC",
                fontSize = 16.sp,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold
            )

            Divider(
                modifier = Modifier
                    .height(64.dp)
                    .width(2.dp),
                color = Color.LightGray,
                thickness = 2.dp
            )

            Text(
                modifier = Modifier
                    .padding(12.dp)
                    .weight(1f),
                text = "REALISTIC",
                fontSize = 16.sp,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold
            )

            Divider(
                modifier = Modifier
                    .height(64.dp)
                    .width(2.dp),
                color = Color.LightGray,
                thickness = 2.dp
            )

            Text(
                modifier = Modifier
                    .padding(12.dp)
                    .weight(1f),
                text = "PESSIMISTIC",
                fontSize = 16.sp,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold
            )
        }

        Divider(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp),
            color = Color.LightGray,
            thickness = 1.dp
        )

        Row() {
            Text(
                modifier = Modifier
                    .padding(12.dp)
                    .weight(1f),
                text = "$optimisticMonths mos",
                fontSize = 12.sp,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold
            )

            Divider(
                modifier = Modifier
                    .height(64.dp)
                    .width(2.dp),
                color = Color.LightGray,
                thickness = 2.dp
            )

            Text(
                modifier = Modifier
                    .padding(12.dp)
                    .weight(1f),
                text = "$realisticMonths mos",
                fontSize = 12.sp,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold
            )

            Divider(
                modifier = Modifier
                    .height(64.dp)
                    .width(2.dp),
                color = Color.LightGray,
                thickness = 2.dp
            )

            Text(
                modifier = Modifier
                    .padding(12.dp)
                    .weight(1f),
                text = "$pessimisticMonths mos",
                fontSize = 12.sp,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold
            )
        }

        Divider(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp),
            color = Color.LightGray,
            thickness = 1.dp
        )

        Row() {
            if (plan != null) {
                Text(
                    modifier = Modifier
                        .padding(12.dp)
                        .weight(1f),
                    text = "(${plan.optimistic.lossPerMonth} $weightUnit / mo.)",
                    fontSize = 12.sp,
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold
                )
            }

            Divider(
                modifier = Modifier
                    .height(64.dp)
                    .width(2.dp),
                color = Color.LightGray,
                thickness = 2.dp
            )

            if (plan != null) {
                Text(
                    modifier = Modifier
                        .padding(12.dp)
                        .weight(1f),
                    text = "(${plan.realistic.lossPerMonth} $weightUnit / mo.)",
                    fontSize = 12.sp,
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold
                )
            }

            Divider(
                modifier = Modifier
                    .height(64.dp)
                    .width(2.dp),
                color = Color.LightGray,
                thickness = 2.dp
            )

            if (plan != null) {
                Text(
                    modifier = Modifier
                        .padding(12.dp)
                        .weight(1f),
                    text = "(${plan.pessimistic.lossPerMonth} $weightUnit" +
                            " / mo.)",
                    fontSize = 12.sp,
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}
