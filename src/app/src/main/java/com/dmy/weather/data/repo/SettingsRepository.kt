package com.dmy.weather.data.repo

import com.dmy.weather.data.data_source.local.data_store.MyDataStore
import com.dmy.weather.data.db.AlertDao
import com.dmy.weather.data.enums.LocationMode
import com.dmy.weather.data.enums.UnitSystem
import com.dmy.weather.data.model.AlertEntity
import com.dmy.weather.data.model.LocationDetails
import com.dmy.weather.data.model.UserSettings
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull

class SettingsRepository(private val myDataStore: MyDataStore, val alertDao: AlertDao) {

    val settingsFlow: Flow<UserSettings> = myDataStore.settingsFlow
    val locationDetailsFlow: Flow<LocationDetails?> = myDataStore.defaultLocationFlow

    suspend fun saveSettings(userSettings: UserSettings) {
        myDataStore.saveSettings(userSettings)
    }

    suspend fun getUnit(): UnitSystem {
        return settingsFlow.first().unit ?: UnitSystem.METRIC
    }

    suspend fun getLocationMode(): LocationMode {
        return settingsFlow.first().locationMode ?: LocationMode.DEFAULT
    }

    suspend fun saveDefaultLocation(locationDetails: LocationDetails) {
        myDataStore.saveDefaultLocation(locationDetails)
    }

    suspend fun getDefaultLocation(): LocationDetails? {
        return locationDetailsFlow.firstOrNull()
    }

    suspend fun saveLastKnownLocation(locationDetails: LocationDetails) {
        myDataStore.saveLastKnownLocation(locationDetails)
    }

    suspend fun getLastKnownLocation(): LocationDetails? {
        return myDataStore.lastKnownLocationFlow.firstOrNull()
    }

    suspend fun updateAlert(alert: AlertEntity) = alertDao.upsert(alert)

    fun getAlerts(): Flow<List<AlertEntity>> = alertDao.getAlerts()

    suspend fun getActiveAlerts(): List<AlertEntity> = alertDao.getActiveAlerts()
}