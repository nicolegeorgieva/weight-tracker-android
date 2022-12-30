package com.weighttracker.persistence.weight

import com.weighttracker.base.Action
import com.weighttracker.base.persistence.AppDataStore
import com.weighttracker.persistence.DataStoreKeys
import javax.inject.Inject

// Writes "weight" in the datastore
class WriteWeightAct @Inject constructor(
    private val appDataStore: AppDataStore,
    private val dataStoreKeys: DataStoreKeys,
) : Action<Double, Unit>() {
    override suspend fun Double.willDo() {
        appDataStore.put(dataStoreKeys.weight, this)
    }
}