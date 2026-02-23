package com.dmy.weather.data.data_source.local.data_store

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.dmy.weather.data.enums.AppLanguage
import com.dmy.weather.data.enums.LocationMode
import com.dmy.weather.data.enums.UnitSystem
import com.dmy.weather.data.model.LocationDetails
import com.dmy.weather.data.model.UserSettings
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.serialization.json.Json

private val Context.dataStore by preferencesDataStore("app_settings")

class MyDataStore(private val context: Context) {
    companion object {
        private val LANG_KEY = stringPreferencesKey("language")
        private val UNITS_KEY = stringPreferencesKey("units")
        private val LOCATION_MODE_KEY = stringPreferencesKey("location_mode")

        private val LOCATION_DETAILS_KEY = stringPreferencesKey("location_details")
    }

    val settingsFlow: Flow<UserSettings> = context.dataStore.data.map { pref ->
        UserSettings(
            lang = AppLanguage.fromName(pref[LANG_KEY]),
            unit = UnitSystem.fromName(pref[UNITS_KEY]),
            locationMode = LocationMode.fromName(pref[LOCATION_MODE_KEY])
        )
    }

    suspend fun saveSettings(userSettings: UserSettings) {
        context.dataStore.edit { pref ->
            userSettings.lang?.let { pref[LANG_KEY] = it.name }
            userSettings.unit?.let { pref[UNITS_KEY] = it.name }
            userSettings.locationMode?.let { pref[LOCATION_MODE_KEY] = it.name }
        }
    }

    val locationDetailsFlow: Flow<LocationDetails?> = context.dataStore.data.map { pref ->
        pref[LOCATION_DETAILS_KEY]?.let {
            Json.decodeFromString<LocationDetails>(it)
        }
    }

    suspend fun saveDefaultLocation(locationDetails: LocationDetails) {
        context.dataStore.edit { pref ->
            pref[LOCATION_DETAILS_KEY] = Json.encodeToString(locationDetails)
        }
    }
}