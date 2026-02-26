package com.dmy.weather.data.data_source.local.settings_data_source

import com.dmy.weather.data.model.LocationDetails
import com.dmy.weather.data.model.UserSettings
import kotlinx.coroutines.flow.Flow

interface SettingsDataSource {

    val settingsFlow: Flow<UserSettings>

    suspend fun saveSettings(userSettings: UserSettings)

    val defaultLocationFlow: Flow<LocationDetails?>

    suspend fun saveDefaultLocation(locationDetails: LocationDetails)

    val lastKnownLocationFlow: Flow<LocationDetails?>

    suspend fun saveLastKnownLocation(locationDetails: LocationDetails)
}