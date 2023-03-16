package com.weighttracker.screen.weightRecords

import com.weighttracker.domain.data.Weight
import java.time.Instant
import java.util.*

data class WeightRecordWithBmi(
    val id: UUID,
    val weight: Weight,
    val date: Instant,
    val bmi: Double?
)

data class WeightRecordsState(
    val startWeight: Double?,
    val latestWeight: Double?,
    val difference: Double?,
    val weightRecords: List<WeightRecordWithBmi>,
    val weightUnit: String,
    val latestBmi: Double?,
    val startBmi: Double?
)