package com.dmy.weather.presentation.home_screen

import com.dmy.weather.data.model.LocationDetails


sealed interface HomeUiState {
    object Loading : HomeUiState
    object NoLocation : HomeUiState
    data class CurrentLocationReady(val location: LocationDetails) : HomeUiState
    data class CustomLocationReady(val location: LocationDetails) : HomeUiState
}