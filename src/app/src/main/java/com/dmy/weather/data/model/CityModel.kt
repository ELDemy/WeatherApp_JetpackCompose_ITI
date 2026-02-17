package com.dmy.weather.data.model

data class CityModel(
    val name: String,
    val localName: String,
    val latitude: Double,
    val longitude: Double,
    val country: String,
    val state: String
)