package com.weighttracker.screen.weightGoal

import androidx.compose.foundation.BorderStroke
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
import com.weighttracker.component.CustomDivider
import com.weighttracker.component.ExpandCollapseArrow
import com.weighttracker.component.Header
import com.weighttracker.component.NumberInputField
import com.weighttracker.domain.NormalWeightRange
import com.weighttracker.formatNumber
import com.weighttracker.navigateTo
import java.text.DecimalFormat
import kotlin.math.abs

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
                CurrentWeightTitleAndValue(
                    currentWeight = state.currentWeight,
                    weightUnit = state.weightUnit
                )
            }

            CustomDivider()

            Spacer(modifier = Modifier.height(16.dp))
        }

        item(key = "goal weight title and input") {
            GoalWeightTitleAndInput(
                goalWeight = state.goalWeight,
                weightUnit = state.weightUnit,
                onGoalWeightChange = {
                    onEvent(WeightGoalEvent.WeightGoalInput(targetWeight = it))
                }
            )

            CustomDivider()

            Spacer(modifier = Modifier.height(24.dp))
        }

        item(key = "weight loss or gain message with value") {
            WeightLoseOrGainMessage(
                weightToLoseOrGainValue = state.weightToLoseOrGain,
                weightUnit = state.weightUnit
            )
        }

        item(key = "ideal weight info and button with its value") {
            state.normalWeightRange?.let {
                IdealWeightMessageAndButton(
                    idealWeight = state.idealWeight,
                    weightUnit = state.weightUnit,
                    onClick = {
                        onEvent(WeightGoalEvent.WeightGoalInput(targetWeight = state.idealWeight))
                    },
                    normalWeightRange = it
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

                Spacer(modifier = Modifier.height(64.dp))
            }
        }
    }
}

@Composable
fun IdealWeightMessageAndButton(
    idealWeight: Double?,
    weightUnit: String,
    onClick: () -> Unit,
    normalWeightRange: NormalWeightRange
) {
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
            val idealWeightFormatted = idealWeight?.let {
                formatNumber(it)
            }

            Button(
                onClick = { onClick() },
                enabled = true,
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFC56767))
            ) {
                Text(text = "$idealWeightFormatted $weightUnit", fontSize = 16.sp)
            }
        }
    }

    if (expandedState) {
        Spacer(modifier = Modifier.height(16.dp))

        IdealWeightMessage(normalWeightRange = normalWeightRange, weightUnit = weightUnit)
    }
}

@Composable
fun IdealWeightMessage(
    normalWeightRange: NormalWeightRange,
    weightUnit: String,

    ) {
    if (normalWeightRange.minWeight > 0 && normalWeightRange.maxWeight > 0
    ) {
        val minWeightFormatted = DecimalFormat("###,###.#")
            .format(normalWeightRange.minWeight)
        val maxWeightFormatted = DecimalFormat("###,###.#")
            .format(normalWeightRange.maxWeight)

        Text(
            text = "Your ideal weight is the arithmetic mean between your minimal and maximum" +
                    " normal weight ($minWeightFormatted - $maxWeightFormatted $weightUnit).",
            fontSize = 16.sp
        )
    }
}

@Composable
fun WeightLoseOrGainMessage(weightToLoseOrGainValue: Double?, weightUnit: String) {
    val weightToLoseOrGain = weightToLoseOrGainValue?.let {
        formatNumber(abs(it))
    }

    if (weightToLoseOrGain != null) {
        Text(
            text = if (weightToLoseOrGainValue < 0) {
                "You have to gain $weightToLoseOrGain $weightUnit to achieve your" +
                        " weight goal."
            } else {
                "You have to lose $weightToLoseOrGain $weightUnit to achieve your" +
                        " weight goal."
            },
            fontSize = 16.sp,
            fontStyle = FontStyle.Italic,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(32.dp))
    }
}

@Composable
fun GoalWeightTitleAndInput(
    goalWeight: Double?,
    weightUnit: String,
    onGoalWeightChange: (Double?) -> Unit
) {
    Row(verticalAlignment = Alignment.Bottom) {
        Text(
            modifier = Modifier.weight(2f),
            text = "Goal weight",
            fontSize = 16.sp
        )

        NumberInputField(
            modifier = Modifier
                .width(80.dp)
                .height(52.dp),
            number = goalWeight,
            placeholder = "",
            onValueChange = {
                onGoalWeightChange(it)
            }
        )

        Spacer(modifier = Modifier.width(4.dp))

        Text(text = weightUnit)
    }
}

@Composable
fun RowScope.CurrentWeightTitleAndValue(currentWeight: Double?, weightUnit: String) {
    Text(
        modifier = Modifier.weight(2f),
        text = "Current weight",
        fontSize = 16.sp
    )

    val currentWeightFormatted = currentWeight?.let { formatNumber(it) }

    Text(
        modifier = Modifier.clickable {
            navigateTo(Screens.BMI)
        },
        text = "$currentWeightFormatted $weightUnit",
        fontSize = 16.sp
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WeightLossPlanCard(plan: WeightLossPlan?, weightUnit: String) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(Color(color = 0xFFFFE5EB)),
        elevation = CardDefaults.cardElevation(12.dp),
        border = BorderStroke(4.dp, Color(0x37FCCACA))

    ) {
        WeightLossPlanCardTitle()

        Spacer(modifier = Modifier.height(8.dp))

        val optimisticMonths = plan?.optimistic?.let { formatNumber(abs(it.totalMonths)) }
        val realisticMonths = plan?.realistic?.let { formatNumber(abs(it.totalMonths)) }
        val pessimisticMonths = plan?.pessimistic?.let { formatNumber(abs(it.totalMonths)) }

        Row() {
            TickIcon(color = Color(0xFFF44336))

            TickIcon(color = Color(0xFF3ECE44))

            TickIcon(color = Color(0xFF575757))
        }

        Row {
            WeightLossPlanOption(text = "OPTIMISTIC")

            VerticalDivider()

            WeightLossPlanOption(text = "REALISTIC")

            VerticalDivider()

            WeightLossPlanOption(text = "PESSIMISTIC")
        }

        HorizontalDivider()

        Row {
            WeightLossPlanCardCell(text = "$optimisticMonths mos")

            VerticalDivider()

            WeightLossPlanCardCell(text = "$realisticMonths mos")

            VerticalDivider()

            WeightLossPlanCardCell(text = "$pessimisticMonths mos")
        }

        HorizontalDivider()

        Row {
            if (plan != null) {
                WeightLossPlanCardCell(text = "(${plan.optimistic.lossPerMonth} $weightUnit / mo.)")
            }

            VerticalDivider()

            if (plan != null) {
                WeightLossPlanCardCell(text = "(${plan.realistic.lossPerMonth} $weightUnit / mo.)")
            }

            VerticalDivider()

            if (plan != null) {
                WeightLossPlanCardCell(
                    text = "(${plan.pessimistic.lossPerMonth} $weightUnit / mo.)"
                )
            }
        }

        Spacer(modifier = Modifier.height(12.dp))
    }
}

@Composable
fun RowScope.TickIcon(color: Color) {
    Icon(
        modifier = Modifier
            .padding(8.dp)
            .weight(1f),
        imageVector = Icons.Default.Check,
        tint = color,
        contentDescription = ""
    )
}

@Composable
fun HorizontalDivider() {
    Divider(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp),
        color = Color(0x57C56767),
        thickness = 1.dp
    )
}

@Composable
fun VerticalDivider() {
    Divider(
        modifier = Modifier
            .height(64.dp)
            .width(2.dp),
        color = Color(0x57C56767),
        thickness = 2.dp
    )
}

@Composable
fun WeightLossPlanCardTitle() {
    Text(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .padding(top = 8.dp),
        text = "W E I G H T    L O S S    P L A N",
        fontWeight = FontWeight.Bold,
        fontSize = 20.sp,
        textAlign = TextAlign.Center,
        color = Color(0xFFC56767)
    )
}

@Composable
fun RowScope.WeightLossPlanCardCell(text: String) {
    Text(
        modifier = Modifier
            .padding(12.dp)
            .weight(1f),
        text = text,
        fontSize = 12.sp,
        textAlign = TextAlign.Center,
        fontWeight = FontWeight.Bold
    )
}

@Composable
fun RowScope.WeightLossPlanOption(text: String) {
    Text(
        modifier = Modifier
            .padding(12.dp)
            .weight(1f),
        text = text,
        fontSize = 14.sp,
        textAlign = TextAlign.Center,
        fontWeight = FontWeight.Bold
    )
}
