package com.weighttracker.screen.waterRecords

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
import com.weighttracker.component.Header
import com.weighttracker.component.NumberInputField
import com.weighttracker.format
import com.weighttracker.persistence.database.waterrecords.WaterRecordEntity
import com.weighttracker.toLocal

@Composable
fun WaterRecordsScreen(screen: Screens.WaterRecords) {
    val viewModel: WaterRecordsViewModel = viewModel()
    val state by viewModel.uiState.collectAsState()

    UI(state = state, onEvent = viewModel::onEvent, screen = screen)
}

@Composable
private fun UI(
    screen: Screens.WaterRecords,
    state: WaterRecordsState,
    onEvent: (WaterRecordsEvent) -> Unit,
) {
    Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 16.dp)) {
        Header(back = screen.backTo, title = "Water records")

        Spacer(modifier = Modifier.height(32.dp))

        LazyColumn() {
            items(items = state.waterRecords) { waterRecordItem ->
                WaterRecordCard(
                    waterRecord = waterRecordItem,
                    onEvent = onEvent,
                    state = state
                )
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}

@Composable
private fun WaterRecordCard(
    waterRecord: WaterRecordEntity,
    onEvent: (WaterRecordsEvent) -> Unit,
    state: WaterRecordsState
) {
    var editCard by remember {
        mutableStateOf(false)
    }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(CircleShape)
            .background(color = Color(0x5B2A337A))
            .clickable { editCard = true }
            .padding(horizontal = 12.dp, vertical = 12.dp)
            .padding(start = 8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (!editCard) {
            Row() {
                Column(modifier = Modifier.weight(1f)) {
                    Text(text = waterRecord.dateTime.toLocal().format("dd. MMM yyyy   HH:mm"))
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        text = waterRecord.water.toString(),
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Bold
                    )
                }
                Button(
                    modifier = Modifier.size(48.dp),
                    onClick = {
                        onEvent(WaterRecordsEvent.DeleteWaterRecord(waterRecord))
                    }, colors = ButtonDefaults.buttonColors(
                        contentColor = Color.White,
                        containerColor = Color(0x232A337A)
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
                var waterInput by remember {
                    mutableStateOf(waterRecord.water)
                }

                Column(modifier = Modifier.weight(1f)) {
                    NumberInputField(
                        number = waterInput,
                        placeholder = "Water: 1, 2, 1.5, ...",
                        onValueChange = {
                            waterInput = it
                        })
                }
                Button(
                    modifier = Modifier.size(48.dp),
                    onClick = {
                        editCard = false
                        onEvent(
                            WaterRecordsEvent.UpdateWaterRecord(
                                newRecord = WaterRecordEntity(
                                    id = waterRecord.id,
                                    dateTime = waterRecord.dateTime,
                                    water = waterInput
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