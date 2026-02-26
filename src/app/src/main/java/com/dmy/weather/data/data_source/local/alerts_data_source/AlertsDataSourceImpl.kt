package com.dmy.weather.data.data_source.local.alerts_data_source

import com.dmy.weather.data.db.AlertDao
import com.dmy.weather.data.model.AlertEntity
import kotlinx.coroutines.flow.Flow

class AlertsDataSourceImpl(private val alertDao: AlertDao) : AlertsDataSource {

    override suspend fun updateAlert(alert: AlertEntity) = alertDao.upsert(alert)

    override fun getAlerts(): Flow<List<AlertEntity>> = alertDao.getAlerts()

    override suspend fun getActiveAlerts(): List<AlertEntity> = alertDao.getActiveAlerts()
}