package com.weighttracker.screen.weightRecords

sealed interface WeightRecordsEvent {
    data class DeleteWeightRecord(val record: WeightRecordWithBmi) : WeightRecordsEvent
    data class UpdateWeightRecord(val newRecord: WeightRecordWithBmi) : WeightRecordsEvent
}