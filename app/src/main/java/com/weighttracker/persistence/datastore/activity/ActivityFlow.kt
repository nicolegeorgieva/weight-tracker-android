package com.weighttracker.persistence.datastore.activity

import com.weighttracker.base.FlowAction
import com.weighttracker.base.persistence.AppDataStore
import com.weighttracker.persistence.datastore.DataStoreKeys
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ActivityFlow @Inject constructor(
    private val appDataStore: AppDataStore,
    private val dataStoreKeys: DataStoreKeys,
) : FlowAction<Unit, String?>() {
    override fun Unit.createFlow(): Flow<String?> =
        appDataStore.get(dataStoreKeys.activity)
}