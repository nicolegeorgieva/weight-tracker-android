package com.weighttracker.persistence.database.activityrecords

import com.weighttracker.base.Action
import com.weighttracker.persistence.database.AppDatabase
import javax.inject.Inject

class WriteActivityRecordAct @Inject constructor(
    private val appDatabase: AppDatabase
) : Action<ActivityRecordEntity, Unit>() {
    override suspend fun action(input: ActivityRecordEntity) {
        appDatabase.activityRecordDao().save(input)
    }
}