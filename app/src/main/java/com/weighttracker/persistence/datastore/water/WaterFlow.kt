package com.weighttracker.persistence.datastore.water

import com.weighttracker.base.FlowAction
import com.weighttracker.base.persistence.AppDataStore
import com.weighttracker.domain.data.Water
import com.weighttracker.persistence.datastore.DataStoreKeys
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import javax.inject.Inject

class WaterFlow @Inject constructor(
    private val appDataStore: AppDataStore,
    private val dataStoreKeys: DataStoreKeys,
    private val waterUnitFlow: WaterUnitFlow
) : FlowAction<Unit, Water?>() {
    override fun Unit.createFlow(): Flow<Water?> = combine(
        appDataStore.get(dataStoreKeys.water),
        waterUnitFlow(Unit)
    ) { waterValue, waterUnit ->
        if (waterValue != null) Water(waterValue, waterUnit) else null
    }
}