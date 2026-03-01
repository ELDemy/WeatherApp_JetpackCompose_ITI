package com.dmy.weather.data.model

import androidx.room.Entity

@Entity(
    tableName = "favLocations",
    primaryKeys = ["longitude", "latitude"]
)
data class CityModel(
    val name: String = "",
    val localName: String = "",
    val latitude: Double = 0.0,
    val longitude: Double = 0.0,
    val country: String = "",
    val state: String = ""
)