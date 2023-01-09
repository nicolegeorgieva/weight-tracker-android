package com.weighttracker.persistence.database.weightrecords

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import java.util.*

@Dao
interface WeightRecordDao {
    // Create or Update
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun save(record: WeightRecordEntity)

    // Delete
    @Query("DELETE FROM weight_records WHERE id = :id")
    suspend fun deleteById(id: UUID)

    // Reads all weight records
    @Query("SELECT * FROM weight_records")
    fun findAll(): Flow<List<WeightRecordEntity>>
}