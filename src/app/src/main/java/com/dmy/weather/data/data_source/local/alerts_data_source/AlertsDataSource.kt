package com.dmy.weather.data.data_source.local.alerts_data_source

import com.dmy.weather.data.model.AlertEntity
import kotlinx.coroutines.flow.Flow

interface AlertsDataSource {
    suspend fun updateAlert(alert: AlertEntity)

    fun getAlerts(): Flow<List<AlertEntity>>

    suspend fun getActiveAlerts(): List<AlertEntity>
}