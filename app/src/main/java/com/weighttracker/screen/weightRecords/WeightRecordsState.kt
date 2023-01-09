package com.weighttracker.screen.weightRecords

import com.weighttracker.persistence.database.weightrecords.WeightRecordEntity

data class WeightRecordsState(
    val weightRecords: List<WeightRecordEntity>
)