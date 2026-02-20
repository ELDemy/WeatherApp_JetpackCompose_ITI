package com.dmy.weather.presentation.settings_screen

import android.util.Log
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.LocaleListCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dmy.weather.data.enums.AppLanguage
import com.dmy.weather.data.enums.LocationMode
import com.dmy.weather.data.enums.UnitSystem
import com.dmy.weather.data.model.UserSettings
import com.dmy.weather.data.repo.SettingsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import org.koin.core.component.getScopeId

class SettingsVM(private val settingsRepo: SettingsRepository) : ViewModel() {
    companion object {
        private const val TAG = "SettingsVM"
        var counter = 1;
    }

    init {
        Log.i(TAG, "SettingsVM created: ${this.getScopeId()} ${counter++} ")
    }

    val settingsState = settingsRepo.settingsFlow.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = UserSettings(
            lang = AppLanguage.DEFAULT,
            unit = UnitSystem.METRIC,
            locationMode = LocationMode.GPS
        )
    )

    fun updateUnitSystem(unitSystem: UnitSystem) {
        viewModelScope.launch {
            settingsRepo.saveSettings(settingsState.value.copy(unit = unitSystem))
        }
    }

    fun updateLanguage(lang: AppLanguage) {
        viewModelScope.launch {
            settingsRepo.saveSettings(settingsState.value.copy(lang = lang))
        }
        AppCompatDelegate.setApplicationLocales(
            LocaleListCompat.forLanguageTags(lang.apiCode)
        )
    }

    fun updateLocationMode(locationMode: LocationMode) {
        viewModelScope.launch {
            settingsRepo.saveSettings(settingsState.value.copy(locationMode = locationMode))
        }
    }

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    fun updateSearch(query: String) {
        _searchQuery.value = query
    }

    val filteredLanguages = _searchQuery
        .map { query ->
            AppLanguage.entries.filter { lang ->
                lang.displayName.contains(query, ignoreCase = true) ||
                        lang.apiCode.contains(query, ignoreCase = true)
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = AppLanguage.entries
        )
}


