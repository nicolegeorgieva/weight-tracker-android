package com.weighttracker.persistence.datastore.weight

import com.weighttracker.base.Action
import com.weighttracker.base.persistence.AppDataStore
import com.weighttracker.domain.data.WeightUnit
import com.weighttracker.persistence.datastore.DataStoreKeys
import javax.inject.Inject

class WriteWeightUnitAct @Inject constructor(
    private val appDataStore: AppDataStore,
    private val dataStoreKeys: DataStoreKeys,
) : Action<WeightUnit, Unit>() {
    override suspend fun action(input: WeightUnit) {
        appDataStore.put(
            dataStoreKeys.kgSelected,
            when (input) {
                WeightUnit.Kg -> true
                WeightUnit.Lb -> false
            }
        )
    }
}