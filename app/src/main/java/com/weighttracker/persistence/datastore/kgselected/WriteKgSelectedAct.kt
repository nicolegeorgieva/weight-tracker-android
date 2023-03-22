package com.weighttracker.persistence.datastore.kgselected

import com.weighttracker.base.Action
import com.weighttracker.base.persistence.AppDataStore
import com.weighttracker.persistence.datastore.DataStoreKeys
import javax.inject.Inject

@Deprecated("old")
class WriteKgSelectedAct @Inject constructor(
    private val appDataStore: AppDataStore,
    private val dataStoreKeys: DataStoreKeys,
) : Action<Boolean, Unit>() {
    override suspend fun action(input: Boolean) {
        appDataStore.put(dataStoreKeys.kgSelected, input)
    }
}