package com.dmy.weather.data.model

data class WeatherModel(
    val cityName: String,
    val country: String,
    val time: String,
    val temperature: Double?,
    val min: Double?,
    val max: Double?,
    val feelsLike: Double?,
    val description: String,
    var iconUrl: String?,
    val humidity: Int?,
    val pressure: Int?,
    val windSpeed: Double?,
    val clouds: Int?
)