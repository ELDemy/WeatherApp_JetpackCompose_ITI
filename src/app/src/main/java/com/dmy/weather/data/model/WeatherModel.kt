package com.dmy.weather.data.model

data class WeatherModel(
    val cityName: String,
    val temperature: Double?,
    val description: String,
    var iconUrl: String?,
    val humidity: String,
    val pressure: String
)