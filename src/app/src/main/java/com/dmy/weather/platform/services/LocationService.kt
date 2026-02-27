package com.dmy.weather.platform.services

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.LocationManager
import android.util.Log
import androidx.core.content.ContextCompat
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.tasks.CancellationTokenSource
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume


class LocationService(val context: Context) {

    @SuppressLint("MissingPermission")
    suspend fun getCurrentLocation(): LatLng? {
        val client = LocationServices.getFusedLocationProviderClient(context)
        return suspendCancellableCoroutine { continuation ->
            val cts = CancellationTokenSource()
            client.getCurrentLocation(Priority.PRIORITY_HIGH_ACCURACY, cts.token)
                .addOnSuccessListener {
                    continuation.resume(it?.let { l ->
                        LatLng(
                            l.latitude,
                            l.longitude
                        )
                    })
                }
                .addOnFailureListener { continuation.resume(null) }
            continuation.invokeOnCancellation { cts.cancel() }
        }
    }

    @SuppressLint("MissingPermission")
    suspend fun getLastKnownLocation(): LatLng? {
        val client = LocationServices.getFusedLocationProviderClient(context)
        return suspendCancellableCoroutine { continuation ->
            client.lastLocation
                .addOnSuccessListener {
                    continuation.resume(it?.let { l ->
                        LatLng(
                            l.latitude,
                            l.longitude
                        )
                    })
                }
                .addOnFailureListener { continuation.resume(null) }
        }
    }

    fun hasPermission(): Boolean {
        return ContextCompat.checkSelfPermission(
            context, Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
    }

    fun isLocationEnabled(): Boolean {
        val lm = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        val gps = lm.isProviderEnabled(LocationManager.GPS_PROVIDER)
        val network = lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
        Log.d("Location", "GPS: $gps, Network: $network")
        return gps || network
    }
}