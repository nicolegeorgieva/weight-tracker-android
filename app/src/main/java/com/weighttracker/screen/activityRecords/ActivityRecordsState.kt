package com.weighttracker.screen.activityRecords

import com.weighttracker.persistence.database.activityrecords.ActivityRecordEntity

data class ActivityRecordsState(
    val activityRecords: List<ActivityRecordEntity>
)