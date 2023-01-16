package com.weighttracker.persistence.database.waterrecords

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.Instant
import java.util.*

@Entity(tableName = "water_records")
data class WaterRecordEntity(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: UUID,
    @ColumnInfo(name = "dateTime")
    val dateTime: Instant,
    @ColumnInfo(name = "water")
    val water: Double
)