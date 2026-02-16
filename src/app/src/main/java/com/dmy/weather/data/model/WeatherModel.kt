package com.dmy.weather.data.model

data class WeatherModel(
    val cityName: String,
    val temperature: String,
    val description: String,
    val iconUrl: String,
    val humidity: String,
    val pressure: String
)