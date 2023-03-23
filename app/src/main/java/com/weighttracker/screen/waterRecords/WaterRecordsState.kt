package com.weighttracker.screen.waterRecords

import com.weighttracker.domain.data.WaterUnit
import com.weighttracker.persistence.database.waterrecords.WaterRecordEntity

data class WaterRecordsState(
    val waterRecords: List<WaterRecordEntity>,
    val waterUnit: WaterUnit
)