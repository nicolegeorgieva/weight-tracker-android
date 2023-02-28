package com.weighttracker.screen.weightRecords

import java.time.Instant
import java.util.*

data class WeightRecordWithBmi(
    val id: UUID,
    val weightInKg: Double,
    val date: Instant,
    val bmi: Double?
)

data class WeightRecordsState(
    val minWeight: Double?,
    val maxWeight: Double?,
    val difference: Double?,
    val weightRecords: List<WeightRecordWithBmi>,
    val weightUnit: String
)