package com.weighttracker.persistence.database.weightrecords

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.Instant
import java.util.*

@Entity(tableName = "weight_records")
data class WeightRecordEntity(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: UUID,
    @ColumnInfo(name = "dateTime")
    val dateTime: Instant,
    @ColumnInfo(name = "weightKg")
    val weightKg: Double
)
