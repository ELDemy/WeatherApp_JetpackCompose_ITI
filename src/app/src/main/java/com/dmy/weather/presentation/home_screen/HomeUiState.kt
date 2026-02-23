package com.dmy.weather.presentation.home_screen

import com.dmy.weather.data.model.LocationDetails


sealed interface HomeUiState {
    object Loading : HomeUiState
    object NoLocation : HomeUiState

    data class OldLocation(
        val location: LocationDetails? = null,
        val warning: String? = null,
        val effect: HomeEffect? = null
    ) : HomeUiState

    data class CurrentLocationReady(
        val location: LocationDetails,
        val warning: String? = null
    ) :
        HomeUiState

    data class CustomLocationReady(
        val location: LocationDetails,
        val warning: String? = null
    ) :
        HomeUiState
}