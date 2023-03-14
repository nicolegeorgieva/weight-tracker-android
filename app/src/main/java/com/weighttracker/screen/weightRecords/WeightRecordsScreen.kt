package com.weighttracker.screen.weightRecords

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.weighttracker.*
import com.weighttracker.R
import com.weighttracker.component.Header
import com.weighttracker.component.NumberInputField
import com.weighttracker.domain.formatBmi
import kotlin.math.abs

@Composable
fun WeightRecordsScreen(screen: Screens.WeightRecords) {
    val viewModel: WeightRecordsViewModel = viewModel()
    val state by viewModel.uiState.collectAsState()

    UI(state = state, onEvent = viewModel::onEvent, screen = screen)
}

@Composable
private fun UI(
    screen: Screens.WeightRecords,
    state: WeightRecordsState,
    onEvent: (WeightRecordsEvent) -> Unit,
) {
    Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 16.dp)) {
        Header(back = screen.backTo, title = "Weight records")

        Spacer(modifier = Modifier.height(12.dp))

        Column {
            if (state.latestWeight != null && state.startWeight != null &&
                state.latestBmi != null && state.startBmi != null
            ) {
                MinMaxWeightBmiGraph(
                    latestWeight = state.latestWeight,
                    startWeight = state.startWeight,
                    weightUnit = state.weightUnit,
                    latestBmi = state.latestBmi,
                    startBmi = state.startBmi
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            if (state.difference != null) {
                val differenceFormatted = formatNumber(abs(state.difference))
                val differenceMessage = if (state.difference < 0) {
                    "+$differenceFormatted ${state.weightUnit} (weight gain)"
                } else {
                    "-$differenceFormatted ${state.weightUnit} (weight loss)"
                }

                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = differenceMessage,
                    color = Color(0xFF287E2C),
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        LazyColumn() {
            items(items = state.weightRecords) { weightRecordItem ->
                WeightRecordBmiCard(
                    weightRecord = weightRecordItem,
                    onEvent = onEvent,
                    state = state
                )
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}

@Composable
fun MinMaxWeightBmiGraph(
    latestWeight: Double,
    startWeight: Double,
    weightUnit: String,
    latestBmi: Double,
    startBmi: Double
) {
    Canvas(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        onDraw = {
            drawLine(
                color = Color.Black,
                start = Offset(x = 0f, y = 0f),
                end = Offset(x = size.width, y = 0f)
            )

            drawCircle(
                color = Color(0xFF3FB444),
                radius = 6.dp.toPx(),
                center = Offset(x = 32.dp.toPx(), y = 0.dp.toPx())
            )

            drawCircle(
                color = Color(0xFFC62828),
                radius = 6.dp.toPx(),
                center = Offset(x = size.width - 32.dp.toPx(), y = 0.dp.toPx())
            )
        }
    )

    val latestWeightFormatted = formatNumber(latestWeight)
    val startWeightFormatted = formatNumber(startWeight)

    Row(modifier = Modifier.fillMaxWidth()) {
        Spacer(modifier = Modifier.width(28.dp))

        Text(
            text = "$latestWeightFormatted $weightUnit",
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.weight(1f))

        Text(
            text = "$startWeightFormatted $weightUnit",
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.width(28.dp))
    }

    Spacer(modifier = Modifier.height(4.dp))

    val latestBmiFormatted = formatBmi(latestBmi)
    val startBmiFormatted = formatBmi(startBmi)

    Row(modifier = Modifier.fillMaxWidth()) {
        Spacer(modifier = Modifier.width(28.dp))

        Text(
            text = "BMI: $latestBmiFormatted",
            color = Color.DarkGray,
            fontSize = 12.sp,
            fontWeight = FontWeight.Light
        )

        Spacer(modifier = Modifier.weight(1f))

        Text(
            text = "BMI: $startBmiFormatted",
            color = Color.DarkGray,
            fontSize = 12.sp,
            fontWeight = FontWeight.Light
        )

        Spacer(modifier = Modifier.width(28.dp))
    }
}

@Composable
private fun WeightRecordBmiCard(
    weightRecord: WeightRecordWithBmi,
    onEvent: (WeightRecordsEvent) -> Unit,
    state: WeightRecordsState
) {
    var editCard by remember {
        mutableStateOf(false)
    }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(CircleShape)
            .background(color = Color(0xFFFAEFF2))
            .clickable { editCard = true }
            .padding(horizontal = 12.dp, vertical = 12.dp)
            .padding(start = 12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (!editCard) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                val formattedWeight = formatNumber(weightRecord.weightInKg)
                val formattedBmi = if (weightRecord.bmi != null) {
                    formatBmi(weightRecord.bmi)
                } else null

                Column(modifier = Modifier.weight(1f)) {
                    Text(text = weightRecord.date.toLocal().format("dd. MMM yyyy   HH:mm"))
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        text = "$formattedWeight ${state.weightUnit}",
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        text = "BMI: $formattedBmi",
                        color = if (weightRecord.bmi != null) {
                            when (weightRecord.bmi) {
                                in 0.0..18.49 -> Color(0xFFA7C7E7)
                                in 18.49..25.0 -> Color(0xFF008006)
                                in 25.0..30.0 -> Color(0xFFD6C209)
                                in 30.0..35.0 -> Color(0xFFFFA500)
                                in 35.0..40.0 -> Color.Red
                                else -> {
                                    Color(0xFFC41E3A)
                                }
                            }
                        } else Color.Black,
                        textAlign = TextAlign.Center
                    )

                }

                Button(
                    modifier = Modifier.size(48.dp),
                    onClick = {
                        onEvent(WeightRecordsEvent.DeleteWeightRecord(weightRecord))
                    }, colors = ButtonDefaults.buttonColors(
                        contentColor = Color.White,
                        containerColor = Color(0x1DF44336)
                    ),
                    enabled = true,
                    shape = CircleShape,
                    contentPadding = PaddingValues(0.dp)
                ) {
                    Text(text = "X")
                }
            }
        } else {
            Row() {
                var weightInput by remember {
                    mutableStateOf(weightRecord.weightInKg)
                }

                Column(modifier = Modifier.weight(1f)) {
                    NumberInputField(
                        number = weightInput,
                        placeholder = "Weight",
                        onValueChange = {
                            weightInput = it
                        }
                    )
                }
                Button(
                    modifier = Modifier.size(48.dp),
                    onClick = {
                        editCard = false
                        onEvent(
                            WeightRecordsEvent.UpdateWeightRecord(
                                newRecord = WeightRecordWithBmi(
                                    id = weightRecord.id,
                                    date = weightRecord.date,
                                    weightInKg = weightInput,
                                    bmi = weightRecord.bmi
                                )
                            )
                        )
                    }, colors = ButtonDefaults.buttonColors(
                        contentColor = Color.White,
                        containerColor = Color(0xFF168A0E)
                    ),
                    enabled = true,
                    shape = CircleShape,
                    contentPadding = PaddingValues(0.dp)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.baseline_check_24),
                        contentDescription = "", tint = Color.White
                    )
                }
            }
        }
    }
}