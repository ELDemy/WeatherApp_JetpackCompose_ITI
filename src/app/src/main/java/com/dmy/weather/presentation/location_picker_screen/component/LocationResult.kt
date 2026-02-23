package com.dmy.weather.presentation.location_picker_screen.component

import com.google.android.gms.maps.model.LatLng

sealed interface LocationResult {
    data class Current(val latLng: LatLng) : LocationResult
    data class LastKnown(val latLng: LatLng) : LocationResult
    object Unavailable : LocationResult
    object LocationServicesOff : LocationResult
    object PermissionDenied : LocationResult
    object PermissionPermanentlyDenied : LocationResult
}