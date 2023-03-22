package com.weighttracker.persistence.datastore.height

import com.weighttracker.base.FlowAction
import com.weighttracker.base.persistence.AppDataStore
import com.weighttracker.domain.data.Height
import com.weighttracker.persistence.datastore.DataStoreKeys
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import javax.inject.Inject

class HeightFlow @Inject constructor(
    private val appDataStore: AppDataStore,
    private val dataStoreKeys: DataStoreKeys,
    private val heightUnitFlow: HeightUnitFlow
) : FlowAction<Unit, Height?>() {
    override fun Unit.createFlow(): Flow<Height?> = combine(
        appDataStore.get(dataStoreKeys.height),
        heightUnitFlow(Unit)
    ) { heightValue, heightUnit ->
        if (heightValue != null) Height(heightValue, heightUnit) else null
    }
}