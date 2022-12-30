package com.weighttracker.persistence.weight

import com.weighttracker.base.FlowAction
import com.weighttracker.base.persistence.AppDataStore
import com.weighttracker.persistence.DataStoreKeys
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

// Reads "weight" from the DataStore
class WeightFlow @Inject constructor(
    private val appDataStore: AppDataStore,
    private val dataStoreKeys: DataStoreKeys,
) : FlowAction<Unit, Double>() {
    override fun Unit.createFlow(): Flow<Double> =
        appDataStore.get(dataStoreKeys.weight).map {
            it ?: 0.0
        }
}