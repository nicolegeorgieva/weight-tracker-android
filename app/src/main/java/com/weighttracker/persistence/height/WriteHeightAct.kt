package com.weighttracker.persistence.height

import com.weighttracker.base.Action
import com.weighttracker.base.persistence.AppDataStore
import com.weighttracker.persistence.DataStoreKeys
import javax.inject.Inject

class WriteHeightAct @Inject constructor(
    private val appDataStore: AppDataStore,
    private val dataStoreKeys: DataStoreKeys,
) : Action<Double, Unit>() {
    override suspend fun Double.willDo() {
        appDataStore.put(dataStoreKeys.height, this)
    }
}