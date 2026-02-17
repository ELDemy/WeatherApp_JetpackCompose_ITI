package com.dmy.weather.data.model

data class DailyForecastModel(
    val cityName: String,
    val forecasts: List<DailyWeatherModel>
)

data class DailyWeatherModel(
    val dateTime: String,
    val tempDay: Int,
    val tempMin: Int,
    val tempMax: Int,
    val condition: String,
    val description: String,
    val icon: String,
    val humidity: Int,
    val windSpeed: Double,
    val rainChance: Int // Percentage 0-100
)