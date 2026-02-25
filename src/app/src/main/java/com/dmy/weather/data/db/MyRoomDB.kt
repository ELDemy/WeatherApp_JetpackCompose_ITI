package com.dmy.weather.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.dmy.weather.data.model.AlertEntity

@Database(entities = [AlertEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun alertDao(): AlertDao

    companion object {
        fun getInstance(context: Context): AppDatabase {
            return Room.databaseBuilder(
                context,
                AppDatabase::class.java,
                "weather_db"
            ).build()
        }
    }
}
