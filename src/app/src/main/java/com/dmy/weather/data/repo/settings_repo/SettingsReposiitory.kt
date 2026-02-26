package com.dmy.weather.data.repo.settings_repo

import com.dmy.weather.data.enums.LocationMode
import com.dmy.weather.data.enums.UnitSystem
import com.dmy.weather.data.model.LocationDetails
import com.dmy.weather.data.model.UserSettings
import kotlinx.coroutines.flow.Flow

interface SettingsRepository {
    val settingsFlow: Flow<UserSettings>

    val locationDetailsFlow: Flow<LocationDetails?>

    suspend fun saveSettings(userSettings: UserSettings)

    suspend fun getUnit(): UnitSystem

    suspend fun getLocationMode(): LocationMode

    suspend fun saveDefaultLocation(locationDetails: LocationDetails)

    suspend fun getDefaultLocation(): LocationDetails?

    suspend fun saveLastKnownLocation(locationDetails: LocationDetails)

    suspend fun getLastKnownLocation(): LocationDetails?
    
}