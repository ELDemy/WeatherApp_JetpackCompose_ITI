package com.dmy.weather.data.mapper

import com.dmy.weather.data.model.LocationDetails
import com.google.android.gms.maps.model.LatLng

fun LatLng.toLocationDetails() = LocationDetails(
    lat = latitude.toString(),
    long = longitude.toString()
)

fun LocationDetails?.toLatLng() =
    if (this?.lat != null && long != null)
        LatLng(lat.toDouble(), long.toDouble())
    else null