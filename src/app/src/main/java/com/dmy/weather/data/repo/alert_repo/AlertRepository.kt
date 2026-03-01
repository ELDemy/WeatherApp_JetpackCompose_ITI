package com.dmy.weather.data.repo.alert_repo

import com.dmy.weather.data.model.AlertEntity
import kotlinx.coroutines.flow.Flow

interface AlertRepository {

    suspend fun updateAlert(alert: AlertEntity)

    fun getAlerts(): Flow<List<AlertEntity>>

    suspend fun getActiveAlerts(): List<AlertEntity>

    suspend fun createAlarms(alerts: List<AlertEntity>? = null): Result<Unit>

}