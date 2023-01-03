package com.weighttracker.persistence.quote

import com.weighttracker.base.FlowAction
import com.weighttracker.base.persistence.AppDataStore
import com.weighttracker.persistence.DataStoreKeys
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class QuoteFlow @Inject constructor(
    private val appDataStore: AppDataStore,
    private val dataStoreKeys: DataStoreKeys,
) : FlowAction<Unit, String?>() {
    override fun Unit.createFlow(): Flow<String?> =
        appDataStore.get(dataStoreKeys.quote)
}