package com.weighttracker.screen.exercise

import androidx.compose.foundation.clickable
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
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.weighttracker.AppTheme
import com.weighttracker.Screens
import com.weighttracker.browser
import com.weighttracker.component.ErrorMessage
import com.weighttracker.component.Header
import com.weighttracker.component.LoadingMessage
import com.weighttracker.domain.data.Exercise
import com.weighttracker.network.RemoteCall

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
                itemsIndexed(items = state.exerciseRequest.data) { index,
                                                                   exerciseItem ->
                    ExerciseInfoCard(index = index, exercise = exerciseItem)
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
fun ExerciseInfoCard(index: Int, exercise: Exercise) {
    val browser = browser()

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
                modifier = Modifier.clickable {
                    if (exercise.youtubeLink != null) {
                        browser.openUri(exercise.youtubeLink)
                    }
                },
                text = exercise.name ?: "",
                fontSize = 16.sp,
                textDecoration = TextDecoration.Underline,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(8.dp))

            exercise.primaryMuscles?.let {
                Text(
                    text = "Primary muscles: ${it.joinToString(", ")}"
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            exercise.secondaryMuscles?.let {
                Text(
                    text = "Secondary muscles: ${it.joinToString(", ")}"
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
                containerColor = Color(0xFF033E44),
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

@Preview
@Composable
private fun Preview() {
    AppTheme {
        UI(
            state = ExerciseState(
                muscle = listOf(
                    "trapezius",
                    "deltoid",
                    "pectoralis major",
                    "triceps",
                    "biceps",
                    "abdominal",
                    "serratus anterior",
                    "latissimus dorsi",
                    "external oblique",
                    "brachioradialis",
                    "finger extensors",
                    "finger flexors",
                    "quadriceps",
                    "hamstrings",
                    "sartorius",
                    "abductors",
                    "gastrocnemius",
                    "tibialis anterior",
                    "soleus",
                    "gluteus medius",
                    "gluteus maximus"
                ),
                selectedMuscle = "biceps",
                exerciseRequest = RemoteCall.Ok(
                    listOf(
                        Exercise(
                            name = "Dumbbell Bicep Curl",
                            primaryMuscles = listOf("biceps"),
                            secondaryMuscles = listOf("deltoid", "trapezius"),
                            youtubeLink = "https://www.youtube.com/watch?v=ykJmrZ5v0Oo&ab_channel=Howcast"
                        ),
                        Exercise(
                            name = "Dumbbell Bicep Curl",
                            primaryMuscles = listOf("biceps"),
                            secondaryMuscles = listOf("deltoid", "trapezius"),
                            youtubeLink = "https://www.youtube.com/watch?v=ykJmrZ5v0Oo&ab_channel=Howcast"
                        )
                    )
                )
            ), onEvent = {})
    }
}