package com.weighttracker.persistence.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.weighttracker.persistence.database.activityrecords.ActivityRecordDao
import com.weighttracker.persistence.database.activityrecords.ActivityRecordEntity
import com.weighttracker.persistence.database.waterrecords.WaterRecordDao
import com.weighttracker.persistence.database.waterrecords.WaterRecordEntity
import com.weighttracker.persistence.database.weightrecords.WeightRecordDao
import com.weighttracker.persistence.database.weightrecords.WeightRecordEntity

@Database(
    entities = [
        WeightRecordEntity::class,
        ActivityRecordEntity::class,
        WaterRecordEntity::class
    ],
    version = 1,
    exportSchema = true,
)
@TypeConverters(
    GeneralTypeConverters::class
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun weightRecordDao(): WeightRecordDao
    abstract fun activityRecordDao(): ActivityRecordDao
    abstract fun waterRecordDao(): WaterRecordDao

    companion object {
        private const val DB_NAME = "weight-tracker.db"

        fun create(applicationContext: Context): AppDatabase {
            return Room.databaseBuilder(
                applicationContext, AppDatabase::class.java, DB_NAME
            ).build()
        }
    }
}