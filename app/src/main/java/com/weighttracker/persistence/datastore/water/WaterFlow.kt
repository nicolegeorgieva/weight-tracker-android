package com.weighttracker.persistence.datastore.water

import com.weighttracker.base.FlowAction
import com.weighttracker.base.persistence.AppDataStore
import com.weighttracker.persistence.datastore.DataStoreKeys
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class WaterFlow @Inject constructor(
    private val appDataStore: AppDataStore,
    private val dataStoreKeys: DataStoreKeys,
) : FlowAction<Unit, Double?>() {
    override fun Unit.createFlow(): Flow<Double?> =
        appDataStore.get(dataStoreKeys.water)
}