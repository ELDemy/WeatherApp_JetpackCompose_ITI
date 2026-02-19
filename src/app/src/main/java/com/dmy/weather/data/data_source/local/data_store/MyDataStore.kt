package com.dmy.weather.data.data_source.local.data_store

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.dmy.weather.data.enums.LocationMode
import com.dmy.weather.data.enums.UnitSystem
import com.dmy.weather.data.model.UserSettings
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class MyDataStore(private val context: Context) {
    companion object {
        private val Context.dataStore by preferencesDataStore("user_settings")
        private val LANG_KEY = stringPreferencesKey("app_language")
        private val UNITS_KEY = stringPreferencesKey("app_units")
        private val LOCATION_KEY = stringPreferencesKey("location_mode")
    }

    val settingsFlow: Flow<UserSettings> = context.dataStore.data.map { pref ->
        UserSettings(
            lang = pref[LANG_KEY] ?: "en",
            unit = pref[UNITS_KEY]?.let { UnitSystem.valueOf(it) } ?: UnitSystem.METRIC,
            locationMode = pref[LOCATION_KEY]?.let { LocationMode.valueOf(it) } ?: LocationMode.GPS
        )
    }

    suspend fun saveSettings(userSettings: UserSettings) {
        context.dataStore.edit { pref ->
            userSettings.lang?.let { pref[LANG_KEY] = it }
            userSettings.unit?.let {
                pref[UNITS_KEY] = it.name
            }
            userSettings.locationMode?.let { pref[LOCATION_KEY] = it.name }
        }
    }
}