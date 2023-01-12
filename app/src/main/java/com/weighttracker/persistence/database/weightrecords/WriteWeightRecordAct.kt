package com.weighttracker.persistence.database.weightrecords

import com.weighttracker.base.Action
import com.weighttracker.persistence.database.AppDatabase
import javax.inject.Inject

class WriteWeightRecordAct @Inject constructor(
    private val appDatabase: AppDatabase
) : Action<WeightRecordEntity, Unit>() {
    override suspend fun action(input: WeightRecordEntity) {
        appDatabase.weightRecordDao().save(input)
    }
}