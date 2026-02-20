package com.dmy.weather.data.model

data class HourlyForecastModel(
    val cityName: String,
    val hourlyItems: List<HourlyWeatherModel>
)

data class HourlyWeatherModel(
    val time: String,
    val temperature: Double?,
    val iconUrl: String?,
    val description: String,
    val clouds: Int?,
)
