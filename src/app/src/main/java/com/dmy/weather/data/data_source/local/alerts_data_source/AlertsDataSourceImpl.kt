package com.dmy.weather.data.data_source.local.alerts_data_source

import com.dmy.weather.data.db.AlertDao
import com.dmy.weather.data.model.AlertModel
import kotlinx.coroutines.flow.Flow

class AlertsDataSourceImpl(private val alertDao: AlertDao) : AlertsDataSource {

    override suspend fun updateAlert(alert: AlertModel) = alertDao.upsert(alert)

    override fun getAlerts(): Flow<List<AlertModel>> = alertDao.getAlerts()

    override suspend fun getActiveAlerts(): List<AlertModel> = alertDao.getActiveAlerts()
}