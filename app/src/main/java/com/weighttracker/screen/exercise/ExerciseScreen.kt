package com.weighttracker.screen.exercise

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.weighttracker.Screens
import com.weighttracker.component.ErrorMessage
import com.weighttracker.component.Header
import com.weighttracker.component.LoadingMessage
import com.weighttracker.network.RemoteCall
import com.weighttracker.network.exercise.ExerciseResponse

@Composable
fun ExerciseScreen() {
    val viewModel: ExerciseViewModel = viewModel()
    val state by viewModel.uiState.collectAsState()
    UI(state = state, onEvent = viewModel::onEvent)
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun UI(
    state: ExerciseState,
    onEvent: (ExerciseEvent) -> Unit,
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
    ) {
        item(key = "header") {
            Header(
                modifier = Modifier.padding(16.dp),
                back = Screens.Settings,
                title = "Exercises"
            )

            Spacer(modifier = Modifier.height(16.dp))
        }

        item(key = "muscle section title") {
            Text(
                modifier = Modifier.padding(horizontal = 16.dp),
                text = "Select one of the following muscles to get exercises for it"
            )
            Spacer(modifier = Modifier.height(8.dp))
        }

        item(key = "muscles grid") {
            FlowRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 14.dp),
            ) {
                state.muscle.forEach { it ->
                    MuscleItem(
                        selected = it == state.selectedMuscle,
                        muscleInput = it
                    ) {
                        onEvent(ExerciseEvent.SelectMuscle(it))
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))
        }

        when (state.exerciseRequest) {
            is RemoteCall.Error -> item {
                ErrorMessage(modifier = Modifier.padding(horizontal = 16.dp)) {
                    onEvent(ExerciseEvent.RetryExerciseRequest)
                }
            }
            RemoteCall.Loading -> item { LoadingMessage() }
            is RemoteCall.Ok -> {
                itemsIndexed(items = state.exerciseRequest.data) { index, exerciseItem ->
                    ExerciseInfoCard(index = index, data = exerciseItem)
                    Spacer(modifier = Modifier.height(16.dp))
                }

                if (state.exerciseRequest.data.isEmpty()) {
                    item {
                        Text(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            text = "There are no exercises for that muscle yet."
                        )
                    }
                }
            }
            null -> {}
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExerciseInfoCard(index: Int, data: ExerciseResponse) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(48.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF033E44),
            contentColor = Color(0xFFFFFFFF)
        )
    ) {
        Spacer(modifier = Modifier.height(24.dp))

        ExerciseInfoTitle(index = index)

        Spacer(modifier = Modifier.height(24.dp))

        Column(
            modifier = Modifier.padding(horizontal = 12.dp)
        ) {
            Text(
                text = data.name ?: "",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(8.dp))

            data.primaryMuscles?.let {
                Text(
                    text = it.joinToString(", "),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            data.secondaryMuscles?.let {
                Text(
                    text = it.joinToString(", "),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))
    }
}

@Composable
fun ExerciseInfoTitle(index: Int) {
    Text(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp),
        text = "E X E R C I S E  #${index + 1}",
        textAlign = TextAlign.Center,
        fontWeight = FontWeight.Bold
    )
}

@Composable
fun MuscleItem(
    selected: Boolean,
    muscleInput: String,
    onClick: () -> Unit
) {
    Button(
        modifier = Modifier.padding(horizontal = 2.dp),
        onClick = onClick,
        colors = if (selected) {
            ButtonDefaults.buttonColors(
                containerColor = Color(0xFF07554C),
                contentColor = Color.White
            )
        } else {
            ButtonDefaults.buttonColors(
                containerColor = Color.Gray,
                contentColor = Color.White
            )
        }
    ) {
        Text(text = muscleInput)
    }
}