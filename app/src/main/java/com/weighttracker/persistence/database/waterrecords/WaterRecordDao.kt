package com.weighttracker.persistence.database.waterrecords

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import java.util.*

@Dao
interface WaterRecordDao {
    // Create or Update
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun save(record: WaterRecordEntity)

    // Delete
    @Query("DELETE FROM water_records WHERE id = :id")
    suspend fun deleteById(id: UUID)

    // Reads all activity records
    @Query("SELECT * FROM water_records")
    fun findAll(): Flow<List<WaterRecordEntity>>
}