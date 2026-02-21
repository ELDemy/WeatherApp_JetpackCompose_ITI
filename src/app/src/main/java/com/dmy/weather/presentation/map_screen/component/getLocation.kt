package com.dmy.weather.presentation.map_screen.component

import android.Manifest
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import com.dmy.weather.presentation.map_screen.hasLocationPermission
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.LatLng

@Composable
fun getLocation(onLocation: (LatLng?) -> Unit) {
    val context = LocalContext.current
    var hasPermission by remember {
        mutableStateOf(hasLocationPermission(context))
    }

    val fusedLocationClient = remember {
        LocationServices.getFusedLocationProviderClient(context)
    }

    val permissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { granted ->
        hasPermission = granted
    }

    LaunchedEffect(hasPermission) {
        if (!hasPermission) {
            permissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        } else {
            fusedLocationClient.lastLocation
                .addOnSuccessListener { location ->
                    onLocation(location?.let { LatLng(it.latitude, it.longitude) })
                }
                .addOnFailureListener {
                    onLocation(null)
                }
        }
    }
}
