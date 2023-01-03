package com.weighttracker.persistence.quote

import com.weighttracker.base.Action
import com.weighttracker.base.persistence.AppDataStore
import com.weighttracker.persistence.DataStoreKeys
import javax.inject.Inject

class WriteQuoteAct @Inject constructor(
    private val appDataStore: AppDataStore,
    private val dataStoreKeys: DataStoreKeys
) : Action<String, Unit>() {
    override suspend fun String.willDo() {
        appDataStore.put(dataStoreKeys.quote, this)
    }
}