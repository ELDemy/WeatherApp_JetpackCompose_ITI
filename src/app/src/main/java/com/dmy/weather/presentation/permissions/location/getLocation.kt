package com.dmy.weather.presentation.permissions.location

import android.Manifest
import android.app.Activity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import com.dmy.weather.platform.services.LocationService
import org.koin.compose.koinInject

@Composable
fun getUserLocation(onResult: (LocationResult) -> Unit) {
    val context = LocalContext.current
    val activity = context as? Activity
    val locationService: LocationService = koinInject()

    var hasPermission by remember {
        mutableStateOf(locationService.hasPermission())
    }

    if (!hasPermission) {
        val launcher = rememberLauncherForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { granted ->
            if (granted) {
                hasPermission = true
            } else {
                val permanentlyDenied = activity?.shouldShowRequestPermissionRationale(
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) == false

                onResult(
                    if (permanentlyDenied) LocationResult.PermissionPermanentlyDenied
                    else LocationResult.PermissionDenied
                )
            }
        }

        LaunchedEffect(Unit) {
            launcher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        }
    }

    LaunchedEffect(hasPermission) {
        if (!hasPermission) return@LaunchedEffect

        if (!locationService.isLocationEnabled()) {
            onResult(LocationResult.LocationServicesOff)
            return@LaunchedEffect
        }

        val current = locationService.getCurrentLocation()
        if (current != null) {
            onResult(LocationResult.Current(current))
            return@LaunchedEffect
        }

        val lastKnown = locationService.getLastKnownLocation()
        onResult(
            if (lastKnown != null) LocationResult.LastKnown(lastKnown)
            else LocationResult.Unavailable
        )
    }
}