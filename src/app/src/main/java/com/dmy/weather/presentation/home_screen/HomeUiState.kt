package com.dmy.weather.presentation.home_screen

import com.dmy.weather.data.enums.LocationMode
import com.dmy.weather.data.model.LocationDetails

data class HomeUiState(
    val location: LocationDetails? = null,
    val locationMode: LocationMode = LocationMode.GPS,
    val isLoadingGPSLocation: Boolean = false,
    val isOldLocation: Boolean = false,
    val isLoading: Boolean = true,
    val isRefreshing: Boolean = false,
    val warning: String? = null,
    val warningEffect: HomeEffect? = null,
    val noLocation: Boolean = false
)