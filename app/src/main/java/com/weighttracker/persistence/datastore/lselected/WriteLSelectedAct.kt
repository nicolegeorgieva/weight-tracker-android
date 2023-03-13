package com.weighttracker.persistence.datastore.lselected

import com.weighttracker.base.Action
import com.weighttracker.base.persistence.AppDataStore
import com.weighttracker.persistence.datastore.DataStoreKeys
import javax.inject.Inject

class WriteLSelectedAct @Inject constructor(
    private val appDataStore: AppDataStore,
    private val dataStoreKeys: DataStoreKeys,
) : Action<Boolean, Unit>() {
    override suspend fun action(input: Boolean) {
        appDataStore.put(dataStoreKeys.lSelected, input)
    }
}