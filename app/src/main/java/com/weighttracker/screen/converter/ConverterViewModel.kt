package com.weighttracker.screen.converter

import com.weighttracker.base.SimpleFlowViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import javax.inject.Inject

@HiltViewModel
class ConverterViewModel @Inject constructor(

) : SimpleFlowViewModel<ConverterState, ConverterEvent>() {
    private val lbFlow = MutableStateFlow(0.0)
    private val kgFlow = MutableStateFlow(0.0)
    private val feetFlow = MutableStateFlow(0.0)
    private val mFlow = MutableStateFlow(0.0)

    override val initialUi = ConverterState(
        lb = null, kg = null, feet = null, m = null
    )

    override val uiFlow: Flow<ConverterState> = combine(
        lbFlow,
        kgFlow,
        feetFlow,
        mFlow
    ) { lb, kg, feet, m ->
        ConverterState(
            lb = lb,
            kg = kg,
            feet = feet,
            m = m
        )
    }

    override suspend fun handleEvent(event: ConverterEvent) {
        when (event) {
            is ConverterEvent.LbInput -> {
                lbFlow.value = event.lb
                kgFlow.value = event.lb * 0.45359237
            }
            is ConverterEvent.KgInput -> {
                kgFlow.value = event.kg
                lbFlow.value = event.kg * 2.205
            }
            is ConverterEvent.FeetInput -> {
                feetFlow.value = event.feet
                mFlow.value = event.feet * 0.3048
            }

            is ConverterEvent.MInput -> {
                mFlow.value = event.m
                feetFlow.value = event.m * 3.28084
            }
            ConverterEvent.Reset -> {
                lbFlow.value = 0.0
                kgFlow.value = 0.0
                feetFlow.value = 0.0
                mFlow.value = 0.0
            }
        }
    }
}