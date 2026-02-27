package com.dmy.weather.presentation.location_search_screen.component.search_field

import com.dmy.weather.data.model.CityModel

data class SearchUiState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val suggestions: List<CityModel>? = null
)