package com.dmy.weather.data.data_source.local.data_store

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.dmy.weather.data.enums.AppLanguage
import com.dmy.weather.data.enums.LocationMode
import com.dmy.weather.data.enums.UnitSystem
import com.dmy.weather.data.model.UserSettings
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore("app_settings")

class MyDataStore(private val context: Context) {
    companion object {
        private val LANG_KEY = stringPreferencesKey("language")
        private val UNITS_KEY = stringPreferencesKey("units")
        private val LOCATION_KEY = stringPreferencesKey("location_mode")
    }

    val settingsFlow: Flow<UserSettings> = context.dataStore.data.map { pref ->
        UserSettings(
            lang = pref[LANG_KEY].let { AppLanguage.fromName(it) },
            unit = pref[UNITS_KEY].let { UnitSystem.fromName(it) },
            locationMode = pref[LOCATION_KEY].let { LocationMode.fromName(it) }
        )
    }

    suspend fun saveSettings(userSettings: UserSettings) {
        context.dataStore.edit { pref ->
            userSettings.lang?.let { pref[LANG_KEY] = it.name }
            userSettings.unit?.let { pref[UNITS_KEY] = it.name }
            userSettings.locationMode?.let { pref[LOCATION_KEY] = it.name }
        }
    }
}