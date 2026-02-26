package com.dmy.weather.data.model

import androidx.room.Entity

@Entity(
    tableName = "favLocations",
    primaryKeys = ["longitude", "latitude"]
)
data class CityModel(
    val name: String = "Cairo",
    val localName: String = "Cairo",
    val latitude: Double = 30.0444,
    val longitude: Double = 31.2357,
    val country: String = "EG",
    val state: String = "Cairo"
)