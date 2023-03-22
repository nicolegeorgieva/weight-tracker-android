package com.weighttracker.screen.settings

import com.weighttracker.base.SimpleFlowViewModel
import com.weighttracker.domain.convertHeight
import com.weighttracker.domain.convertWater
import com.weighttracker.domain.data.*
import com.weighttracker.persistence.datastore.height.HeightFlow
import com.weighttracker.persistence.datastore.height.WriteHeightAct
import com.weighttracker.persistence.datastore.lselected.LSelectedFlow
import com.weighttracker.persistence.datastore.lselected.WriteLSelectedAct
import com.weighttracker.persistence.datastore.mselected.MSelectedFlow
import com.weighttracker.persistence.datastore.mselected.WriteMSelectedAct
import com.weighttracker.persistence.datastore.water.WaterFlow
import com.weighttracker.persistence.datastore.water.WriteWaterAct
import com.weighttracker.persistence.datastore.weight.WeightFlow
import com.weighttracker.persistence.datastore.weight.WeightUnitFlow
import com.weighttracker.persistence.datastore.weight.WriteWeightAct
import com.weighttracker.persistence.datastore.weight.WriteWeightUnitAct
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val weightUnitFlow: WeightUnitFlow,
    private val writeWeightUnitAct: WriteWeightUnitAct,
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
        weightUnit = WeightUnit.Kg, m = true, l = true
    )

    override val uiFlow: Flow<SettingsState> = combine(
        weightUnitFlow(Unit),
        mSelectedFlow(Unit),
        lSelectedFlow(Unit)
    ) { weightUnit, m, l ->
        SettingsState(
            weightUnit = weightUnit,
            m = m,
            l = l
        )
    }

    override suspend fun handleEvent(event: SettingsEvent) {
        when (event) {
            is SettingsEvent.ChangeWeightUnit -> {
                writeWeightUnitAct(event.weightUnit) //it saves kg/lb on the phone
            }

            is SettingsEvent.MSelect -> {
                val mAlreadySelected = uiState.value.m
                val currentHeight = heightFlow(Unit).first()

                writeMSelectedAct(event.m)

                if (currentHeight != null) {
                    val convertedHeight = if (mAlreadySelected && !event.m) {
                        // m to ft
                        convertHeight(Height(currentHeight, HeightUnit.M), HeightUnit.Ft).value
                    } else if (!mAlreadySelected && event.m) {
                        // ft to m
                        convertHeight(Height(currentHeight, HeightUnit.Ft), HeightUnit.M).value
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
                        // l to gal
                        convertWater(Water(currentWaterValue, WaterUnit.L), WaterUnit.Gal).value
                    } else if (!lAlreadySelected && event.l) {
                        // gal to l
                        convertWater(Water(currentWaterValue, WaterUnit.Gal), WaterUnit.L).value
                    } else {
                        currentWaterValue
                    }

                    writeWaterAct(convertedWaterUnit)
                }
            }
        }
    }
}