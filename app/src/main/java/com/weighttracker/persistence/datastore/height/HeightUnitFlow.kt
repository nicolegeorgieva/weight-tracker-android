package com.weighttracker.persistence.datastore.height

import com.weighttracker.base.FlowAction
import com.weighttracker.base.persistence.AppDataStore
import com.weighttracker.domain.data.HeightUnit
import com.weighttracker.persistence.datastore.DataStoreKeys
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class HeightUnitFlow @Inject constructor(
    private val appDataStore: AppDataStore,
    private val dataStoreKeys: DataStoreKeys,
) : FlowAction<Unit, HeightUnit>() {
    override fun Unit.createFlow(): Flow<HeightUnit> =
        appDataStore.get(dataStoreKeys.mSelected).map {
            if (it == true || it == null) HeightUnit.M else HeightUnit.Ft
        }
}