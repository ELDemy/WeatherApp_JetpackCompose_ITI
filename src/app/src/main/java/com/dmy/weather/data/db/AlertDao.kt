package com.dmy.weather.data.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.dmy.weather.data.model.AlertEntity
import kotlinx.coroutines.flow.Flow


@Dao
interface AlertDao {
    @Query("SELECT * FROM alerts")
    fun getAlerts(): Flow<List<AlertEntity>>

    @Query("SELECT * FROM alerts WHERE status = 1")
    suspend fun getActiveAlerts(): List<AlertEntity>

    @Query("SELECT * FROM alerts WHERE type = :type")
    suspend fun getAlertByType(type: String): AlertEntity

    @Upsert
    suspend fun upsert(alert: AlertEntity)

    @Upsert
    suspend fun upsertAll(vararg alerts: AlertEntity)

    @Delete
    suspend fun delete(alert: AlertEntity)
}