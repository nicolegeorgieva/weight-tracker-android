package com.weighttracker.persistence.datastore.water

import com.weighttracker.base.Action
import com.weighttracker.base.persistence.AppDataStore
import com.weighttracker.domain.data.WaterUnit
import com.weighttracker.persistence.datastore.DataStoreKeys
import javax.inject.Inject

class WriteWaterUnitAct @Inject constructor(
    private val appDataStore: AppDataStore,
    private val dataStoreKeys: DataStoreKeys,
) : Action<WaterUnit, Unit>() {
    override suspend fun action(input: WaterUnit) {
        appDataStore.put(
            dataStoreKeys.lSelected,
            when (input) {
                WaterUnit.L -> true
                WaterUnit.Gal -> false
            }
        )
    }
}