package com.weighttracker.screen.waterRecords

import com.weighttracker.persistence.database.waterrecords.WaterRecordEntity

sealed interface WaterRecordsEvent {
    data class DeleteWaterRecord(val record: WaterRecordEntity) : WaterRecordsEvent
    data class UpdateWaterRecord(val newRecord: WaterRecordEntity) : WaterRecordsEvent
}