package com.dmy.weather.data.dto

import com.google.gson.annotations.SerializedName

data class GeocodingCityDTO(
    val name: String,
    @SerializedName("local_names")
    val localNames: Map<String, String>?,
    val lat: Double,
    val lon: Double,
    val country: String,
    val state: String?
)