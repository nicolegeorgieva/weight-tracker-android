package com.weighttracker.persistence.datastore.height

import com.weighttracker.base.Action
import com.weighttracker.base.persistence.AppDataStore
import com.weighttracker.domain.data.Height
import com.weighttracker.persistence.datastore.DataStoreKeys
import javax.inject.Inject

class WriteHeightAct @Inject constructor(
    private val appDataStore: AppDataStore,
    private val dataStoreKeys: DataStoreKeys,
    private val writeHeightUnitAct: WriteHeightUnitAct,
) : Action<Height, Unit>() {
    override suspend fun action(input: Height) {
        appDataStore.put(dataStoreKeys.height, input.value)
        writeHeightUnitAct(input.unit)
    }
}