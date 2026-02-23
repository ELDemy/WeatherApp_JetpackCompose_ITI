package com.dmy.weather.presentation.home_screen

import com.dmy.weather.data.model.LocationDetails


sealed interface HomeState {
    object Loading : HomeState
    object NoLocation : HomeState
    data class LocationReady(val location: LocationDetails) : HomeState
}