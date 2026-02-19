package com.dmy.weather.presentation.settings_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dmy.weather.data.enums.LocationMode
import com.dmy.weather.data.enums.UnitSystem
import com.dmy.weather.data.model.UserSettings
import com.dmy.weather.data.repo.SettingsRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class SettingsVM(private val settingsRepo: SettingsRepository) : ViewModel() {

    val settingsState = settingsRepo.settingsFlow.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = UserSettings(
            lang = "en",
            unit = UnitSystem.METRIC,
            locationMode = LocationMode.GPS
        )
    )

    fun updateUnitSystem(unitSystem: UnitSystem) {
        viewModelScope.launch {
            settingsRepo.saveSettings(settingsState.value.copy(unit = unitSystem))
        }
    }

    fun updateLanguage(lang: String) {
        viewModelScope.launch {
            settingsRepo.saveSettings(settingsState.value.copy(lang = lang))
        }
    }

    fun updateLocationMode(locationMode: LocationMode) {
        viewModelScope.launch {
            settingsRepo.saveSettings(settingsState.value.copy(locationMode = locationMode))
        }
    }
}


