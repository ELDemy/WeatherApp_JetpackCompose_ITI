package com.dmy.weather.data.dto

import com.google.gson.annotations.SerializedName

data class GeocodingCityDTO(
    val name: String = "Cairo",
    @SerializedName("local_names")
    val localNames: Map<String, String>? = mapOf(
        "gd" to "Cairo",
        "ru" to "Каир",
        "se" to "Cairo",
        "qu" to "Qahira",
        "ht" to "Li Kè"
    ),
    val lat: Double = 30.0444,
    val lon: Double = 31.2357,
    val country: String = "EG",
    val state: String? = "Cairo"
)