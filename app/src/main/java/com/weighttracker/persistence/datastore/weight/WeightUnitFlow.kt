package com.weighttracker.persistence.datastore.weight

import com.weighttracker.base.FlowAction
import com.weighttracker.base.persistence.AppDataStore
import com.weighttracker.domain.data.WeightUnit
import com.weighttracker.persistence.datastore.DataStoreKeys
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class WeightUnitFlow @Inject constructor(
    private val appDataStore: AppDataStore,
    private val dataStoreKeys: DataStoreKeys,
) : FlowAction<Unit, WeightUnit>() {
    override fun Unit.createFlow(): Flow<WeightUnit> =
        appDataStore.get(dataStoreKeys.kgSelected).map {
            if (it == true || it == null) WeightUnit.Kg else WeightUnit.Lb
        }
}