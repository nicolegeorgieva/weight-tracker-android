package com.weighttracker.screen.settings

import com.weighttracker.base.SimpleFlowViewModel
import com.weighttracker.domain.convertHeight
import com.weighttracker.domain.convertWater
import com.weighttracker.domain.convertWeight
import com.weighttracker.domain.data.HeightUnit
import com.weighttracker.domain.data.WaterUnit
import com.weighttracker.domain.data.WeightUnit
import com.weighttracker.persistence.datastore.height.HeightFlow
import com.weighttracker.persistence.datastore.height.HeightUnitFlow
import com.weighttracker.persistence.datastore.height.WriteHeightAct
import com.weighttracker.persistence.datastore.height.WriteHeightUnitAct
import com.weighttracker.persistence.datastore.water.WaterFlow
import com.weighttracker.persistence.datastore.water.WaterUnitFlow
import com.weighttracker.persistence.datastore.water.WriteWaterAct
import com.weighttracker.persistence.datastore.water.WriteWaterUnitAct
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
    private val weightFlow: WeightFlow,
    private val writeWeightAct: WriteWeightAct,
    private val weightUnitFlow: WeightUnitFlow,
    private val writeWeightUnitAct: WriteWeightUnitAct,
    private val heightFlow: HeightFlow,
    private val writeHeightAct: WriteHeightAct,
    private val heightUnitFlow: HeightUnitFlow,
    private val writeHeightUnitAct: WriteHeightUnitAct,
    private val waterFlow: WaterFlow,
    private val writeWaterAct: WriteWaterAct,
    private val waterUnitFlow: WaterUnitFlow,
    private val writeWaterUnitAct: WriteWaterUnitAct

) : SimpleFlowViewModel<SettingsState, SettingsEvent>() {
    override val initialUi = SettingsState(
        weightUnit = WeightUnit.Kg, heightUnit = HeightUnit.M, waterUnit = WaterUnit.L
    )

    override val uiFlow: Flow<SettingsState> = combine(
        weightUnitFlow(Unit),
        heightUnitFlow(Unit),
        waterUnitFlow(Unit)
    ) { weightUnit, heightUnit, waterUnit ->
        SettingsState(
            weightUnit = weightUnit,
            heightUnit = heightUnit,
            waterUnit = waterUnit
        )
    }

    override suspend fun handleEvent(event: SettingsEvent) {
        when (event) {
            is SettingsEvent.ChangeWeightUnit -> {
                val currentWeight = weightFlow(Unit).first()
                if (currentWeight != null) {
                    writeWeightAct(convertWeight(currentWeight, event.weightUnit))
                }

                writeWeightUnitAct(event.weightUnit) //it saves kg/lb on the phone
            }

            is SettingsEvent.ChangeHeightUnit -> {
                val currentHeight = heightFlow(Unit).first()
                if (currentHeight != null) {
                    writeHeightAct(convertHeight(currentHeight, event.heightUnit))
                }

                writeHeightUnitAct(event.heightUnit)
            }

            is SettingsEvent.ChangeWaterUnit -> {
                val currentWater = waterFlow(Unit).first()

                if (currentWater != null) {
                    writeWaterAct(convertWater(currentWater, event.waterUnit))
                }

                writeWaterUnitAct(event.waterUnit)
            }
        }
    }
}