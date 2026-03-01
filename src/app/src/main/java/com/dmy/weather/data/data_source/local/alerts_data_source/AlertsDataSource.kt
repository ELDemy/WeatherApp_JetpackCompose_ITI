package com.dmy.weather.data.data_source.local.alerts_data_source

import com.dmy.weather.data.model.AlertModel
import kotlinx.coroutines.flow.Flow

interface AlertsDataSource {
    suspend fun updateAlert(alert: AlertModel)

    fun getAlerts(): Flow<List<AlertModel>>

    suspend fun getActiveAlerts(): List<AlertModel>
}