package com.dmy.weather.presentation.weather_details_screen

import com.dmy.weather.data.model.DailyForecastModel
import com.dmy.weather.data.model.HourlyForecastModel
import com.dmy.weather.data.model.WeatherModel


data class WeatherUiState(
    val currentWeather: UiState<WeatherModel> = UiState(),
    val hourlyForecast: UiState<HourlyForecastModel> = UiState(),
    val dailyForecast: UiState<DailyForecastModel> = UiState(),
)


data class UiState<out T>(
    val isLoading: Boolean = true,
    val data: T? = null,
    val error: String? = null,
)