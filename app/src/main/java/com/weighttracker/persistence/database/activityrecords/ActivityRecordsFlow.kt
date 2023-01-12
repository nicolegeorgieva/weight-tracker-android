package com.weighttracker.persistence.database.activityrecords

import com.weighttracker.base.FlowAction
import com.weighttracker.persistence.database.AppDatabase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ActivityRecordsFlow @Inject constructor(
    private val appDatabase: AppDatabase
) : FlowAction<Unit, List<ActivityRecordEntity>>() {
    override fun Unit.createFlow(): Flow<List<ActivityRecordEntity>> =
        appDatabase.activityRecordDao().findAll()
}