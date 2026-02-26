package com.dmy.weather.presentation.favorites_screen

import com.dmy.weather.data.model.CityModel

data class FavoritesUiState(
    val isLoading: Boolean = true,
    val data: List<CityModel>? = null,
    val error: String? = null
)