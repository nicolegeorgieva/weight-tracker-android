package com.weighttracker.screen.settings

import com.weighttracker.base.SimpleFlowViewModel
import com.weighttracker.domain.convertHeight
import com.weighttracker.domain.convertWater
import com.weighttracker.domain.convertWeight
import com.weighttracker.domain.data.HeightUnit
import com.weighttracker.domain.data.Water
import com.weighttracker.domain.data.WaterUnit
import com.weighttracker.domain.data.WeightUnit
import com.weighttracker.persistence.datastore.height.HeightFlow
import com.weighttracker.persistence.datastore.height.HeightUnitFlow
import com.weighttracker.persistence.datastore.height.WriteHeightAct
import com.weighttracker.persistence.datastore.height.WriteHeightUnitAct
import com.weighttracker.persistence.datastore.lselected.LSelectedFlow
import com.weighttracker.persistence.datastore.lselected.WriteLSelectedAct
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
    private val heightUnitFlow: HeightUnitFlow,
    private val writeHeightUnitAct: WriteHeightUnitAct,
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
        weightUnit = WeightUnit.Kg, heightUnit = HeightUnit.M, l = true
    )

    override val uiFlow: Flow<SettingsState> = combine(
        weightUnitFlow(Unit),
        heightUnitFlow(Unit),
        lSelectedFlow(Unit)
    ) { weightUnit, heightUnit, l ->
        SettingsState(
            weightUnit = weightUnit,
            heightUnit = heightUnit,
            l = l
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