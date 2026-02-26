package com.dmy.weather.data.repo.alert_repo

import com.dmy.weather.data.data_source.local.alerts_data_source.AlertsDataSource
import com.dmy.weather.data.model.AlertEntity
import kotlinx.coroutines.flow.Flow

class AlertRepositoryImpl(
    private val alertsDataSource: AlertsDataSource
) : AlertRepository {

    override suspend fun updateAlert(alert: AlertEntity) = alertsDataSource.updateAlert(alert)

    override fun getAlerts(): Flow<List<AlertEntity>> = alertsDataSource.getAlerts()

    override suspend fun getActiveAlerts(): List<AlertEntity> = alertsDataSource.getActiveAlerts()
}