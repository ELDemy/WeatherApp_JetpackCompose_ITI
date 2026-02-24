package com.dmy.weather.presentation.search_screen

import com.dmy.weather.data.model.CityModel

data class SearchUiState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val suggestions: List<CityModel>? = null
)