package com.dmy.weather.presentation.home_screen

import com.dmy.weather.data.model.LocationDetails


sealed interface HomeState {
    object Loading : HomeState
    object NoLocation : HomeState
    data class CurrentLocationReady(val location: LocationDetails) : HomeState
    data class CustomLocationReady(val location: LocationDetails) : HomeState
}