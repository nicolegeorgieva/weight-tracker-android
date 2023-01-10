package com.weighttracker.screen.settings

import com.weighttracker.base.SimpleFlowViewModel
import com.weighttracker.persistence.datastore.kgselected.KgSelectedFlow
import com.weighttracker.persistence.datastore.kgselected.WriteKgSelectedAct
import com.weighttracker.persistence.datastore.mselected.MSelectedFlow
import com.weighttracker.persistence.datastore.mselected.WriteMSelectedAct
import com.weighttracker.persistence.datastore.weight.WeightFlow
import com.weighttracker.persistence.datastore.weight.WriteWeightAct
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val kgSelectedFlow: KgSelectedFlow,
    private val writeKgSelectedAct: WriteKgSelectedAct,
    private val mSelectedFlow: MSelectedFlow,
    private val writeMSelectedAct: WriteMSelectedAct,
    private val weightFlow: WeightFlow,
    private val writeWeightAct: WriteWeightAct
) : SimpleFlowViewModel<SettingsState, SettingsEvent>() {
    override val initialUi = SettingsState(
        kg = true, m = true
    )

    override val uiFlow: Flow<SettingsState> = combine(
        kgSelectedFlow(Unit),
        mSelectedFlow(Unit),
    ) { kg, m ->
        SettingsState(
            kg = kg,
            m = m
        )
    }

    override suspend fun handleEvent(event: SettingsEvent) {
        when (event) {
            is SettingsEvent.KgSelect -> {
                val kgAlreadySelected = uiState.value.kg //if kgSelected is true
                val currentWeight = weightFlow(Unit).first()

                writeKgSelectedAct(event.kg) //it saves kg/lb on the phone
                if (currentWeight != null) {
                    val convertedWeight = if (kgAlreadySelected && !event.kg) {
                        currentWeight * 2.205 // to lbs
                    } else if (!kgAlreadySelected && event.kg) {
                        currentWeight * 0.45359237
                    } else {
                        currentWeight
                    }
                    writeWeightAct(convertedWeight)
                }
            }
            is SettingsEvent.MSelect -> {
                writeMSelectedAct(event.m)
            }
        }
    }
}