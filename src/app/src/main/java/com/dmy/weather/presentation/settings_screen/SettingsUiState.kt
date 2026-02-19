package com.dmy.weather.presentation.settings_screen

import com.dmy.weather.data.enums.LocationMode
import com.dmy.weather.data.enums.Switch
import com.dmy.weather.data.enums.UnitSystem

data class SettingsUiStates(
    val language: String = "English",
    val locationMode: LocationMode = LocationMode.GPS,
    val unitSystem: UnitSystem = UnitSystem.METRIC,
    val notificationType: Switch = Switch.OFF,
)
