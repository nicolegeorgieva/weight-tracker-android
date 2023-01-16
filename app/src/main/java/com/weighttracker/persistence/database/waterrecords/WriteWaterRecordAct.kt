package com.weighttracker.persistence.database.waterrecords

import com.weighttracker.base.Action
import com.weighttracker.persistence.database.AppDatabase
import javax.inject.Inject

class WriteWaterRecordAct @Inject constructor(
    private val appDatabase: AppDatabase
) : Action<WaterRecordEntity, Unit>() {
    override suspend fun action(input: WaterRecordEntity) {
        appDatabase.waterRecordDao().save(input)
    }
}