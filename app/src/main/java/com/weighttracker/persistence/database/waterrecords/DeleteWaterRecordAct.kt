package com.weighttracker.persistence.database.waterrecords

import com.weighttracker.base.Action
import com.weighttracker.persistence.database.AppDatabase
import java.util.*
import javax.inject.Inject

class DeleteWaterRecordAct @Inject constructor(
    private val appDatabase: AppDatabase
) : Action<UUID, Unit>() {
    override suspend fun action(input: UUID) {
        appDatabase.waterRecordDao().deleteById(input)
    }
}