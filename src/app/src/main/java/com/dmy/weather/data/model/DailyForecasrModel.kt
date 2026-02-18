package com.dmy.weather.data.model

data class DailyForecastModel(
    val cityName: String,
    val forecasts: List<DailyWeatherModel>
)

data class DailyWeatherModel(
    val dateTime: String,
    val tempDay: String,
    val tempMin: String,
    val tempMax: String,
    val condition: String,
    val description: String,
    val icon: String?,
    val humidity: String,
    val windSpeed: String,
    val rainChance: String
)