package com.weighttracker.screen.weightGoal

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
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
    Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 16.dp)) {
        Header(back = Screens.Settings, title = "Weight goal")

        Spacer(modifier = Modifier.height(32.dp))

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

                Button(onClick = {
                    onEvent(WeightGoalEvent.WeightGoalInput(targetWeight = state.idealWeight))
                }, enabled = true) {
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

        if (state.plan != null) {
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

                val optimisticMonths = formatNumber(state.plan.optimistic.totalMonths)
                val realisticMonths = formatNumber(state.plan.realistic.totalMonths)
                val pessimisticMonths = formatNumber(state.plan.pessimistic.totalMonths)

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
                    Text(
                        modifier = Modifier
                            .padding(12.dp)
                            .weight(1f),
                        text = "(${state.plan.optimistic.lossPerMonth} ${state.weightUnit} / mo.)",
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
                        text = "(${state.plan.realistic.lossPerMonth} ${state.weightUnit} / mo.)",
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
                        text = "(${state.plan.pessimistic.lossPerMonth} ${state.weightUnit}" +
                                " / mo.)",
                        fontSize = 12.sp,
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}