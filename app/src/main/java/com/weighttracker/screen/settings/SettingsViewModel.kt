package com.weighttracker.screen.settings

import com.weighttracker.base.SimpleFlowViewModel
import com.weighttracker.persistence.datastore.height.HeightFlow
import com.weighttracker.persistence.datastore.height.WriteHeightAct
import com.weighttracker.persistence.datastore.kgselected.KgSelectedFlow
import com.weighttracker.persistence.datastore.kgselected.WriteKgSelectedAct
import com.weighttracker.persistence.datastore.lselected.LSelectedFlow
import com.weighttracker.persistence.datastore.lselected.WriteLSelectedAct
import com.weighttracker.persistence.datastore.mselected.MSelectedFlow
import com.weighttracker.persistence.datastore.mselected.WriteMSelectedAct
import com.weighttracker.persistence.datastore.water.WaterFlow
import com.weighttracker.persistence.datastore.water.WriteWaterAct
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
    private val writeWeightAct: WriteWeightAct,
    private val heightFlow: HeightFlow,
    private val writeHeightAct: WriteHeightAct,
    private val lSelectedFlow: LSelectedFlow,
    private val writeLSelectedAct: WriteLSelectedAct,
    private val waterFlow: WaterFlow,
    private val writeWaterAct: WriteWaterAct
) : SimpleFlowViewModel<SettingsState, SettingsEvent>() {
    override val initialUi = SettingsState(
        kg = true, m = true, l = true
    )

    override val uiFlow: Flow<SettingsState> = combine(
        kgSelectedFlow(Unit),
        mSelectedFlow(Unit),
        lSelectedFlow(Unit)
    ) { kg, m, l ->
        SettingsState(
            kg = kg,
            m = m,
            l = l
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
                val mAlreadySelected = uiState.value.m
                val currentHeight = heightFlow(Unit).first()

                writeMSelectedAct(event.m)
                if (currentHeight != null) {
                    val convertedHeight = if (mAlreadySelected && !event.m) {
                        currentHeight * 3.28084
                    } else if (!mAlreadySelected && event.m) {
                        currentHeight * 0.3048
                    } else {
                        currentHeight
                    }
                    writeHeightAct(convertedHeight)
                }
            }
            is SettingsEvent.LSelect -> {
                val lAlreadySelected = uiState.value.l
                val currentWaterValue = waterFlow(Unit).first()

                writeLSelectedAct(event.l)
                if (currentWaterValue != null) {
                    val convertedWaterUnit = if (lAlreadySelected && !event.l) {
                        currentWaterValue * 0.264172 //l to gallons
                    } else if (!lAlreadySelected && event.l) {
                        currentWaterValue * 3.785412 //gallons to l
                    } else {
                        currentWaterValue
                    }
                    writeWaterAct(convertedWaterUnit)
                }
            }
        }
    }
}