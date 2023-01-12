package com.weighttracker.persistence.datastore.quote

import com.weighttracker.base.Action
import com.weighttracker.base.persistence.AppDataStore
import com.weighttracker.persistence.datastore.DataStoreKeys
import javax.inject.Inject

class WriteQuoteAct @Inject constructor(
    private val appDataStore: AppDataStore,
    private val dataStoreKeys: DataStoreKeys
) : Action<String, Unit>() {
    override suspend fun action(input: String) {
        appDataStore.put(dataStoreKeys.quote, input)
    }
}