package com.weighttracker.screen.weightRecords

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.weighttracker.Screens
import com.weighttracker.component.BackButton
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
                WeightRecordCard(weightRecord = weightRecordItem, onEvent = onEvent)
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}

@Composable
private fun WeightRecordCard(
    weightRecord: WeightRecordEntity,
    onEvent: (WeightRecordsEvent) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp)
            .background(color = Color.LightGray),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row() {
            Column(modifier = Modifier.weight(1f)) {
                Text(text = weightRecord.dateTime.toLocal().format("dd. MMM yyyy   HH:mm"))
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = "${weightRecord.weightKg}",
                    textAlign = TextAlign.Center
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
    }
}