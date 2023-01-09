package com.weighttracker.persistence.database

import androidx.room.TypeConverter
import com.weighttracker.toUUID
import java.time.Instant
import java.util.*

class GeneralTypeConverters {
    // region Instant
    @TypeConverter
    fun ser(instant: Instant): Long = instant.epochSecond

    @TypeConverter
    fun instant(epochSecond: Long): Instant = Instant.ofEpochSecond(epochSecond)
    // endregion

    @TypeConverter
    fun ser(uuid: UUID): String = uuid.toString()

    @TypeConverter
    fun uuid(string: String): UUID = string.toUUID()
}