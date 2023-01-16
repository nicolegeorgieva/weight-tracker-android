package com.weighttracker.persistence.database.waterrecords

import com.weighttracker.base.FlowAction
import com.weighttracker.persistence.database.AppDatabase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class WaterRecordsFlow @Inject constructor(
    private val appDatabase: AppDatabase
) : FlowAction<Unit, List<WaterRecordEntity>>() {
    override fun Unit.createFlow(): Flow<List<WaterRecordEntity>> =
        appDatabase.waterRecordDao().findAll()
}