package com.dmy.weather.data.repo

import com.dmy.weather.data.data_source.local.data_store.MyDataStore
import com.dmy.weather.data.enums.UnitSystem
import com.dmy.weather.data.model.UserSettings
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first

class SettingsRepository(private val myDataStore: MyDataStore) {

    val settingsFlow: Flow<UserSettings> = myDataStore.settingsFlow

    suspend fun saveSettings(userSettings: UserSettings) {
        myDataStore.saveSettings(userSettings)
    }

    suspend fun getUnit(): UnitSystem {
        return settingsFlow.first().unit ?: UnitSystem.METRIC
    }
}