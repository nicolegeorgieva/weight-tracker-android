package com.weighttracker.persistence.datastore.weightGoal

import com.weighttracker.base.Action
import com.weighttracker.base.persistence.AppDataStore
import com.weighttracker.persistence.datastore.DataStoreKeys
import javax.inject.Inject

class WriteWeightGoalAct @Inject constructor(
    private val appDataStore: AppDataStore,
    private val dataStoreKeys: DataStoreKeys,
) : Action<Double, Unit>() {
    override suspend fun Double.willDo() {
        appDataStore.put(dataStoreKeys.weightGoal, this)
    }
}