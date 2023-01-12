package com.weighttracker.persistence.database.activityrecords

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import java.util.*

@Dao
interface ActivityRecordDao {
    // Create or Update
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun save(record: ActivityRecordEntity)

    // Delete
    @Query("DELETE FROM activity_records WHERE id = :id")
    suspend fun deleteById(id: UUID)

    // Reads all activity records
    @Query("SELECT * FROM activity_records")
    fun findAll(): Flow<List<ActivityRecordEntity>>
}