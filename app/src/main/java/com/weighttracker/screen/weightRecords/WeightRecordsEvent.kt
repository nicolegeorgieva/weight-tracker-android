package com.weighttracker.screen.weightRecords

import com.weighttracker.persistence.database.weightrecords.WeightRecordEntity

sealed interface WeightRecordsEvent {
    data class DeleteWeightRecord(val record: WeightRecordEntity) : WeightRecordsEvent
}