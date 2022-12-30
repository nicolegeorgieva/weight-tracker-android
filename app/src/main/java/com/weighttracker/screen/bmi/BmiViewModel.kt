package com.weighttracker.screen.bmi

import com.weighttracker.base.SimpleFlowViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

@HiltViewModel
class BmiViewModel @Inject constructor(

) : SimpleFlowViewModel<BmiState, BmiEvent>() {
    override val initialUi = BmiState(
        weight = 0.0
    )

    override val uiFlow = flow {
        emit(initialUi)
    }

    override suspend fun handleEvent(event: BmiEvent) {
        TODO("Not yet implemented")
    }
}