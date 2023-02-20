package com.weighttracker.screen.weightRecords

import java.time.Instant
import java.util.*

data class WeightRecordWithBmi(
    val id: UUID,
    val weightInKg: Double,
    val date: Instant,
    val bmi: String?
)

data class WeightRecordsState(
    val weightRecords: List<WeightRecordWithBmi>,
    val weightUnit: String
)