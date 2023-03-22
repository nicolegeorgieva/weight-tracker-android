package com.weighttracker.persistence.datastore.height

import com.weighttracker.base.Action
import com.weighttracker.base.persistence.AppDataStore
import com.weighttracker.domain.data.HeightUnit
import com.weighttracker.persistence.datastore.DataStoreKeys
import javax.inject.Inject

class WriteHeightUnitAct @Inject constructor(
    private val appDataStore: AppDataStore,
    private val dataStoreKeys: DataStoreKeys,
) : Action<HeightUnit, Unit>() {
    override suspend fun action(input: HeightUnit) {
        appDataStore.put(
            dataStoreKeys.mSelected,
            when (input) {
                HeightUnit.M -> true
                HeightUnit.Ft -> false
            }
        )
    }
}