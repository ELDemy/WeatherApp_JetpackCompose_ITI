package com.dmy.weather.data.data_source.local

import com.dmy.weather.data.db.AlertDao
import com.dmy.weather.data.model.AlertEntity
import kotlinx.coroutines.flow.Flow

class AlertsDataSource(private val alertDao: AlertDao) {

    suspend fun updateAlert(alert: AlertEntity) = alertDao.upsert(alert)

    fun getAlerts(): Flow<List<AlertEntity>> = alertDao.getAlerts()

    suspend fun getActiveAlerts(): List<AlertEntity> = alertDao.getActiveAlerts()
}