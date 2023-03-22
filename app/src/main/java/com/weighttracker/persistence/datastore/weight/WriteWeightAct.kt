package com.weighttracker.persistence.datastore.weight

import com.weighttracker.base.Action
import com.weighttracker.base.persistence.AppDataStore
import com.weighttracker.domain.data.Weight
import com.weighttracker.persistence.datastore.DataStoreKeys
import javax.inject.Inject

// Writes "weight" in the datastore
class WriteWeightAct @Inject constructor(
    private val appDataStore: AppDataStore,
    private val dataStoreKeys: DataStoreKeys,
    private val writeWeightUnitAct: WriteWeightUnitAct
) : Action<Weight, Unit>() {
    override suspend fun action(input: Weight) {
        appDataStore.put(dataStoreKeys.weight, input.value)
        writeWeightUnitAct(input.unit)
    }
}