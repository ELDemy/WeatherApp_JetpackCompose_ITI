package com.dmy.weather.data.model

data class WeatherModel(
    val cityName: String,
    val country: String,
    val time: String,
    val temperature: String,
    val min: String,
    val max: String,
    val feelsLike: String,
    val description: String,
    var iconUrl: String?,
    val humidity: String,
    val pressure: String,
    val windSpeed: String,
    val clouds: String
)