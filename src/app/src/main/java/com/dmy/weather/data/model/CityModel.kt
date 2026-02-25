package com.dmy.weather.data.model

import androidx.room.Entity

@Entity(
    tableName = "favLocations",
    primaryKeys = ["longitude", "latitude"]
)
data class CityModel(
    val name: String,
    val localName: String,
    val latitude: Double,
    val longitude: Double,
    val country: String,
    val state: String
)