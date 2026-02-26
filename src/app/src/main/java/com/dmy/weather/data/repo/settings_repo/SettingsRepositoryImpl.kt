package com.dmy.weather.data.repo.settings_repo

import com.dmy.weather.data.data_source.local.settings_data_source.SettingsDataSource
import com.dmy.weather.data.enums.LocationMode
import com.dmy.weather.data.enums.UnitSystem
import com.dmy.weather.data.model.LocationDetails
import com.dmy.weather.data.model.UserSettings
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull

class SettingsRepositoryImpl(
    private val settingsDataSource: SettingsDataSource
) : SettingsRepository {

    override val settingsFlow: Flow<UserSettings> = settingsDataSource.settingsFlow
    override val locationDetailsFlow: Flow<LocationDetails?> =
        settingsDataSource.defaultLocationFlow

    override suspend fun saveSettings(userSettings: UserSettings) {
        settingsDataSource.saveSettings(userSettings)
    }

    override suspend fun getUnit(): UnitSystem {
        return settingsFlow.first().unit ?: UnitSystem.METRIC
    }

    override suspend fun getLocationMode(): LocationMode {
        return settingsFlow.first().locationMode ?: LocationMode.Companion.DEFAULT
    }

    override suspend fun saveDefaultLocation(locationDetails: LocationDetails) {
        settingsDataSource.saveDefaultLocation(locationDetails)
    }

    override suspend fun getDefaultLocation(): LocationDetails? {
        return locationDetailsFlow.firstOrNull()
    }

    override suspend fun saveLastKnownLocation(locationDetails: LocationDetails) {
        settingsDataSource.saveLastKnownLocation(locationDetails)
    }

    override suspend fun getLastKnownLocation(): LocationDetails? {
        return settingsDataSource.lastKnownLocationFlow.firstOrNull()
    }
}