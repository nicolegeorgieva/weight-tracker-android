package com.weighttracker.persistence.datastore.water

import com.weighttracker.base.FlowAction
import com.weighttracker.base.persistence.AppDataStore
import com.weighttracker.domain.data.WaterUnit
import com.weighttracker.persistence.datastore.DataStoreKeys
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class WaterUnitFlow @Inject constructor(
    private val appDataStore: AppDataStore,
    private val dataStoreKeys: DataStoreKeys,
) : FlowAction<Unit, WaterUnit>() {
    override fun Unit.createFlow(): Flow<WaterUnit> =
        appDataStore.get(dataStoreKeys.lSelected).map {
            if (it == true || it == null) WaterUnit.L else WaterUnit.Gal
        }
}