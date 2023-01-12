package com.weighttracker.persistence.database.activityrecords

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.Instant
import java.util.*

@Entity(tableName = "activity_records")
data class ActivityRecordEntity(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: UUID,
    @ColumnInfo(name = "dateTime")
    val dateTime: Instant,
    @ColumnInfo(name = "activity")
    val activity: String
)