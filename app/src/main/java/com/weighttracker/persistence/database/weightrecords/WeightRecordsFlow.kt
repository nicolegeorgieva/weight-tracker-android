package com.weighttracker.persistence.database.weightrecords

import com.weighttracker.base.FlowAction
import com.weighttracker.persistence.database.AppDatabase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class WeightRecordsFlow @Inject constructor(
    private val appDatabase: AppDatabase
) : FlowAction<Unit, List<WeightRecordEntity>>() {
    override fun Unit.createFlow(): Flow<List<WeightRecordEntity>> =
        appDatabase.weightRecordDao().findAll()
}