package com.weighttracker.persistence.kgselected

import com.weighttracker.base.FlowAction
import com.weighttracker.base.persistence.AppDataStore
import com.weighttracker.persistence.DataStoreKeys
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class KgSelectedFlow @Inject constructor(
    private val appDataStore: AppDataStore,
    private val dataStoreKeys: DataStoreKeys,
) : FlowAction<Unit, Boolean>() {
    override fun Unit.createFlow(): Flow<Boolean> =
        appDataStore.get(dataStoreKeys.kgSelected).map {
            it ?: true //if from phone is null it returns true
        }
}