package com.dmy.weather.data.model

import kotlinx.serialization.Serializable

@Serializable
data class LocationDetails(
    val city: String? = null,
    val long: String? = null,
    val lat: String? = null,
)