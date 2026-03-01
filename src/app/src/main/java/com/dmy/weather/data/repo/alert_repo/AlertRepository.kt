package com.dmy.weather.data.repo.alert_repo

import com.dmy.weather.data.model.AlertModel
import kotlinx.coroutines.flow.Flow

interface AlertRepository {

    suspend fun updateAlert(alert: AlertModel)

    fun getAlerts(): Flow<List<AlertModel>>

    suspend fun getActiveAlerts(): List<AlertModel>

    suspend fun createAlarms(alerts: List<AlertModel>? = null): Result<Unit>

}