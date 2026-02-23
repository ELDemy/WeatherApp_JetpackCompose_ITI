package com.dmy.weather.data.model

import com.google.android.gms.maps.model.LatLng
import kotlinx.serialization.Serializable

@Serializable
data class LocationDetails(
    val city: String? = null,
    val long: String? = null,
    val lat: String? = null,
) : java.io.Serializable

fun LatLng.toLocationDetails() = LocationDetails(
    lat = latitude.toString(),
    long = longitude.toString()
)

fun LocationDetails?.toLatLng() =
    if (this?.lat != null && long != null)
        LatLng(lat.toDouble(), long.toDouble())
    else null