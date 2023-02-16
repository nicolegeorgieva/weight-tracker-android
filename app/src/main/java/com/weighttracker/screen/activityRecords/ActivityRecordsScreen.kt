package com.weighttracker.screen.activityRecords

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
import com.weighttracker.component.InputField
import com.weighttracker.format
import com.weighttracker.persistence.database.activityrecords.ActivityRecordEntity
import com.weighttracker.toLocal

@Composable
fun ActivityRecordsScreen(screen: Screens.ActivityRecords) {
    val viewModel: ActivityRecordsViewModel = viewModel()
    val state by viewModel.uiState.collectAsState()

    UI(state = state, onEvent = viewModel::onEvent, screen = screen)
}

@Composable
private fun UI(
    screen: Screens.ActivityRecords,
    state: ActivityRecordsState,
    onEvent: (ActivityRecordsEvent) -> Unit,
) {
    Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 16.dp)) {
        BackButton(screens = screen.backTo)

        Spacer(modifier = Modifier.height(32.dp))

        LazyColumn() {
            items(items = state.activityRecords) { activityRecordItem ->
                ActivityRecordCard(
                    activityRecord = activityRecordItem,
                    onEvent = onEvent,
                    state = state
                )
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}

@Composable
private fun ActivityRecordCard(
    activityRecord: ActivityRecordEntity,
    onEvent: (ActivityRecordsEvent) -> Unit,
    state: ActivityRecordsState
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
                    Text(text = activityRecord.dateTime.toLocal().format("dd. MMM yyyy   HH:mm"))
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        text = activityRecord.activity,
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Bold
                    )
                }
                Button(
                    modifier = Modifier.size(48.dp),
                    onClick = {
                        onEvent(ActivityRecordsEvent.DeleteActivityRecord(activityRecord))
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
                var activityInput by remember {
                    mutableStateOf(activityRecord.activity)
                }

                Column(modifier = Modifier.weight(1f)) {
                    InputField(
                        value = activityInput,
                        placeholder = "Activity",
                        onValueChange = {
                            activityInput = it
                        }
                    )
                }
                Button(
                    modifier = Modifier.size(48.dp),
                    onClick = {
                        editCard = false
                        onEvent(
                            ActivityRecordsEvent.UpdateActivityRecord(
                                newRecord = ActivityRecordEntity(
                                    id = activityRecord.id,
                                    dateTime = activityRecord.dateTime,
                                    activity = activityInput
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