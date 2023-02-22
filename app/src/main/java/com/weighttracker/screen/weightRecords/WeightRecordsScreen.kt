package com.weighttracker.screen.weightRecords

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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.weighttracker.*
import com.weighttracker.R
import com.weighttracker.component.BackButton
import com.weighttracker.component.NumberInputField
import com.weighttracker.domain.formatBmi

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
        BackButton(screens = screen.backTo)

        Spacer(modifier = Modifier.height(32.dp))

        LazyColumn() {
            items(items = state.weightRecords) { weightRecordItem ->
                WeightRecordCard(
                    weightRecord = weightRecordItem, onEvent = onEvent,
                    state = state
                )
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}

@Composable
private fun WeightRecordCard(
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
            .background(color = Color(0x61BE94A0))
            .clickable { editCard = true }
            .padding(horizontal = 12.dp, vertical = 12.dp)
            .padding(start = 8.dp),
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
                                in 18.5..25.0 -> Color(0xFF4CBB17)
                                in 25.0..30.0 -> Color(0xFFE2D02B)
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
                        containerColor = Color(0x17F44336)
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