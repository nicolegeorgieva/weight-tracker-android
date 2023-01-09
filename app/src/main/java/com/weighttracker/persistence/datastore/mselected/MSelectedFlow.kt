package com.weighttracker.persistence.datastore.mselected

import com.weighttracker.base.FlowAction
import com.weighttracker.base.persistence.AppDataStore
import com.weighttracker.persistence.datastore.DataStoreKeys
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class MSelectedFlow @Inject constructor(
    private val appDataStore: AppDataStore,
    private val dataStoreKeys: DataStoreKeys,
) : FlowAction<Unit, Boolean>() {
    override fun Unit.createFlow(): Flow<Boolean> =
        appDataStore.get(dataStoreKeys.mSelected).map {
            it ?: true //if from phone is null it returns true
        }
}