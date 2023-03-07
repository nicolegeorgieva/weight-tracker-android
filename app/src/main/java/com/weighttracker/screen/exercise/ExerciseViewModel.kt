package com.weighttracker.screen.exercise

import com.weighttracker.base.SimpleFlowViewModel
import com.weighttracker.domain.network.mapExerciseResponse
import com.weighttracker.flattenLatest
import com.weighttracker.network.NetworkError
import com.weighttracker.network.RemoteCall
import com.weighttracker.network.exercise.ExerciseRequest
import com.weighttracker.network.exercise.ExerciseRequestInput
import com.weighttracker.network.exercise.ExerciseResponse
import com.weighttracker.network.mapSuccess
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

@HiltViewModel
class ExerciseViewModel @Inject constructor(
    private val exerciseRequest: ExerciseRequest,
) : SimpleFlowViewModel<ExerciseState, ExerciseEvent>() {
    override val initialUi = ExerciseState(
        muscle = emptyList(),
        selectedMuscle = null,
        exerciseRequest = RemoteCall.Loading
    )

    private val muscleFlow = flowOf(
        listOf(
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
        )
    )

    private val selectedMuscleFlow = MutableStateFlow<String?>(initialUi.selectedMuscle)

    override val uiFlow: Flow<ExerciseState> = combine(
        muscleFlow,
        selectedMuscleFlow,
        exerciseRequestFlow()
    ) { muscle, selectedMuscle, request ->
        ExerciseState(
            muscle = muscle,
            selectedMuscle = selectedMuscle,
            exerciseRequest = request?.mapSuccess {
                mapExerciseResponse(it)
            }
        )
    }

    private fun exerciseRequestFlow(): Flow<RemoteCall<NetworkError, List<ExerciseResponse>>?> =
        combine(
            selectedMuscleFlow, selectedMuscleFlow
        ) { selectedMuscle, _ ->
            if (selectedMuscle == null) {
                flowOf(null)
            } else {
                val input = ExerciseRequestInput(
                    muscle = selectedMuscle
                )
                exerciseRequest.flow(input = input)
            }
        }.flattenLatest()


    override suspend fun handleEvent(event: ExerciseEvent) {
        when (event) {
            ExerciseEvent.RetryExerciseRequest -> exerciseRequest.retry()
            is ExerciseEvent.SelectMuscle -> selectedMuscleFlow.value = event.muscle
        }
    }
}