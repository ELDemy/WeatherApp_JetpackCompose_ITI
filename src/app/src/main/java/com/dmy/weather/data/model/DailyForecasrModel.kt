package com.dmy.weather.data.model

data class DailyForecastModel(
    val cityName: String,
    val forecasts: List<DailyWeatherModel>
)

data class DailyWeatherModel(
    val dateTime: String,
    val tempDay: Double?,
    val tempMin: Double?,
    val tempMax: Double?,
    val condition: String,
    val description: String,
    val icon: Int?,
    val bg: Int?,
    val humidity: Int?,
    val windSpeed: Double?,
    val rainChance: Double?
)