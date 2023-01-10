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
import com.weighttracker.R
import com.weighttracker.Screens
import com.weighttracker.component.BackButton
import com.weighttracker.component.NumberInputField
import com.weighttracker.format
import com.weighttracker.persistence.database.weightrecords.WeightRecordEntity
import com.weighttracker.toLocal

@Composable
fun WeightRecordsScreen() {
    val viewModel: WeightRecordsViewModel = viewModel()
    val state by viewModel.uiState.collectAsState()

    UI(state = state, onEvent = viewModel::onEvent)
}

@Composable
private fun UI(
    state: WeightRecordsState,
    onEvent: (WeightRecordsEvent) -> Unit,
) {
    Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 16.dp)) {
        BackButton(screens = Screens.Settings)

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
    weightRecord: WeightRecordEntity,
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
            .background(color = Color(0xFFE6E6E6))
            .clickable { editCard = true }
            .padding(horizontal = 12.dp, vertical = 12.dp)
            .padding(start = 8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (!editCard) {
            Row() {
                Column(modifier = Modifier.weight(1f)) {
                    Text(text = weightRecord.dateTime.toLocal().format("dd. MMM yyyy   HH:mm"))
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        text = "${weightRecord.weightKg} ${state.weightUnit}",
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Bold
                    )
                }
                Button(
                    modifier = Modifier.size(48.dp),
                    onClick = {
                        onEvent(WeightRecordsEvent.DeleteWeightRecord(weightRecord))
                    }, colors = ButtonDefaults.buttonColors(
                        contentColor = Color.White,
                        containerColor = Color(0xFFFF1010)
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
                    mutableStateOf(weightRecord.weightKg)
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
                                newRecord = WeightRecordEntity(
                                    id = weightRecord.id,
                                    dateTime = weightRecord.dateTime,
                                    weightKg = weightInput
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