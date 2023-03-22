package com.weighttracker.persistence.datastore.weight

import com.weighttracker.base.FlowAction
import com.weighttracker.base.persistence.AppDataStore
import com.weighttracker.domain.data.Weight
import com.weighttracker.persistence.datastore.DataStoreKeys
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import javax.inject.Inject

// Reads "weight" from the DataStore
class WeightFlow @Inject constructor(
    private val appDataStore: AppDataStore,
    private val dataStoreKeys: DataStoreKeys,
    private val weightUnitFlow: WeightUnitFlow
) : FlowAction<Unit, Weight?>() {
    override fun Unit.createFlow(): Flow<Weight?> = combine(
        appDataStore.get(dataStoreKeys.weight),
        weightUnitFlow(Unit)
    ) { weightValue, weightUnit ->
        if (weightValue != null) Weight(weightValue, weightUnit) else null
    }
}