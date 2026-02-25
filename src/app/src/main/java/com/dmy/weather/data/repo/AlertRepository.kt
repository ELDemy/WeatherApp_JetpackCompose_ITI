package com.dmy.weather.data.repo

import com.dmy.weather.data.data_source.local.AlertsDataSource
import com.dmy.weather.data.model.AlertEntity
import kotlinx.coroutines.flow.Flow

class AlertRepository(private val alertsDataSource: AlertsDataSource) {

    suspend fun updateAlert(alert: AlertEntity) = alertsDataSource.updateAlert(alert)

    fun getAlerts(): Flow<List<AlertEntity>> = alertsDataSource.getAlerts()

    suspend fun getActiveAlerts(): List<AlertEntity> = alertsDataSource.getActiveAlerts()
}