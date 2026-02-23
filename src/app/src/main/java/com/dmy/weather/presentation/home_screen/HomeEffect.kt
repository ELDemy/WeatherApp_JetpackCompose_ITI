package com.dmy.weather.presentation.home_screen

import com.dmy.weather.data.model.LocationDetails

sealed interface HomeEffect {
    object RequestGpsLocation : HomeEffect
    data class GetLocationFromMap(val currentLocation: LocationDetails?) : HomeEffect
    data class ShowWarning(val message: String) : HomeEffect
    object OpenLocationSettings : HomeEffect
    object OpenAppSettings : HomeEffect
}