package com.dmy.weather.data.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.dmy.weather.data.model.AlertModel
import kotlinx.coroutines.flow.Flow


@Dao
interface AlertDao {
    @Query("SELECT * FROM alerts")
    fun getAlerts(): Flow<List<AlertModel>>

    @Query("SELECT * FROM alerts WHERE status = 1")
    suspend fun getActiveAlerts(): List<AlertModel>

    @Query("SELECT * FROM alerts WHERE type = :type")
    suspend fun getAlertByType(type: String): AlertModel

    @Upsert
    suspend fun upsert(alert: AlertModel)

    @Upsert
    suspend fun upsertAll(vararg alerts: AlertModel)

    @Delete
    suspend fun delete(alert: AlertModel)
}