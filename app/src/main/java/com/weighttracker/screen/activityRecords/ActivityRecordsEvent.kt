package com.weighttracker.screen.activityRecords

import com.weighttracker.persistence.database.activityrecords.ActivityRecordEntity

sealed interface ActivityRecordsEvent {
    data class DeleteActivityRecord(val record: ActivityRecordEntity) : ActivityRecordsEvent
    data class UpdateActivityRecord(val newRecord: ActivityRecordEntity) : ActivityRecordsEvent
}