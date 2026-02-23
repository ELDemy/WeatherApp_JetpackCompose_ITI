package com.dmy.weather.presentation.location_picker_screen.component

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
import com.dmy.weather.utils.LocationUtils


@Composable
fun getUserLocation(onResult: (LocationResult) -> Unit) {
    val context = LocalContext.current

    var currentPermission by remember { mutableStateOf<Boolean>(LocationUtils.hasPermission(context)) }
    var hasPermission by remember { mutableStateOf<Boolean?>(null) }

    when (currentPermission) {
        true -> hasPermission = true

        false -> {
            val launcher = rememberLauncherForActivityResult(
                ActivityResultContracts.RequestPermission()
            )
            { granted -> hasPermission = granted }

            LaunchedEffect(Unit) {
                launcher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
            }
        }
    }

    LaunchedEffect(hasPermission) {
        when (hasPermission) {
            null -> return@LaunchedEffect
            false -> {
                onResult(LocationResult.Unavailable)
                return@LaunchedEffect
            }

            true -> {
                if (!LocationUtils.isLocationEnabled(context)) {
                    onResult(LocationResult.LocationServicesOff)
                    return@LaunchedEffect
                }

                val current = LocationUtils.getCurrentLocation(context)
                if (current != null) {
                    onResult(LocationResult.Current(current))
                    return@LaunchedEffect
                }

                val lastKnown = LocationUtils.getLastKnownLocation(context)
                if (lastKnown != null) {
                    onResult(LocationResult.LastKnown(lastKnown))
                } else {
                    onResult(LocationResult.Unavailable)
                }
            }
        }
    }
}
