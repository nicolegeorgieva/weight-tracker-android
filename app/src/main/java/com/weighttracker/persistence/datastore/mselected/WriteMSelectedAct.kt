package com.weighttracker.persistence.datastore.mselected

import com.weighttracker.base.Action
import com.weighttracker.base.persistence.AppDataStore
import com.weighttracker.persistence.datastore.DataStoreKeys
import javax.inject.Inject

class WriteMSelectedAct @Inject constructor(
    private val appDataStore: AppDataStore,
    private val dataStoreKeys: DataStoreKeys,
) : Action<Boolean, Unit>() {
    override suspend fun Boolean.willDo() {
        appDataStore.put(dataStoreKeys.mSelected, this)
    }
}