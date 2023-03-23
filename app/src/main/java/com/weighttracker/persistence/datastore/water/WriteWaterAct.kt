package com.weighttracker.persistence.datastore.water

import com.weighttracker.base.Action
import com.weighttracker.base.persistence.AppDataStore
import com.weighttracker.domain.data.Water
import com.weighttracker.persistence.datastore.DataStoreKeys
import javax.inject.Inject

class WriteWaterAct @Inject constructor(
    private val appDataStore: AppDataStore,
    private val dataStoreKeys: DataStoreKeys,
    private val writeWaterUnitAct: WriteWaterUnitAct
) : Action<Water, Unit>() {
    override suspend fun action(input: Water) {
        appDataStore.put(dataStoreKeys.water, input.value)
        writeWaterUnitAct(input.unit)
    }
}