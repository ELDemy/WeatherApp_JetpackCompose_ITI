package com.dmy.weather.presentation.home_screen

sealed interface HomeEffect {
    object RequestGpsLocation : HomeEffect
    data class ShowWarning(val message: String) : HomeEffect
    object OpenLocationSettings : HomeEffect
}