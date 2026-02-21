package com.dmy.weather.presentation.map_screen

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MyLocation
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import com.dmy.weather.R
import com.dmy.weather.presentation.map_screen.component.ConfirmButton
import com.dmy.weather.presentation.map_screen.component.MapsSearchField
import com.dmy.weather.presentation.map_screen.component.getLocation
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState

private const val TAG = "MapScreen"

fun hasLocationPermission(context: Context): Boolean {
    return ContextCompat.checkSelfPermission(
        context, Manifest.permission.ACCESS_FINE_LOCATION
    ) == PackageManager.PERMISSION_GRANTED
}

@Composable
fun LocationPickerScreen(navController: NavController) {
    val context = LocalContext.current
    val pickedLocation = remember { mutableStateOf<LatLng?>(null) }
    val showSuggestions = remember { mutableStateOf(false) }
    var hasPermission by remember { mutableStateOf(hasLocationPermission(context)) }
    var mapLoaded by remember { mutableStateOf(false) }
    var userLatLng by remember { mutableStateOf<LatLng?>(null) }

    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(userLatLng ?: LatLng(30.0444, 31.2357), 10f)
    }

    getLocation { location ->
        location?.let {
            hasPermission = true
            userLatLng = it
            if (pickedLocation.value == null) {
                pickedLocation.value = it
            }
        }
    }

    LaunchedEffect(mapLoaded, userLatLng) {
        if (mapLoaded && userLatLng != null) {
            cameraPositionState.animate(
                CameraUpdateFactory.newLatLngZoom(userLatLng!!, 14f)
            )
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        GoogleMap(
            modifier = Modifier.fillMaxSize(),
            cameraPositionState = cameraPositionState,
            properties = MapProperties(isMyLocationEnabled = hasPermission),
            uiSettings = MapUiSettings(myLocationButtonEnabled = hasPermission),
            onMapLoaded = { mapLoaded = true },
            onMapClick = { latLng ->
                pickedLocation.value = latLng
                showSuggestions.value = false
            },
            content = {
                pickedLocation.value?.let {
                    Marker(state = MarkerState(position = it))
                }
            }
        )

        MapsSearchField(
            showSuggestions = showSuggestions,
            cameraPositionState = cameraPositionState,
            pickedLocation = pickedLocation,
            modifier = Modifier.align(Alignment.TopCenter)
        )

        FloatingActionButton(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(end = 16.dp, bottom = 115.dp)
                .size(32.dp),
            containerColor = colorResource(R.color.white),
            onClick = {
                userLatLng?.let {
                    if (mapLoaded) {
                        cameraPositionState.move(
                            CameraUpdateFactory.newLatLngZoom(it, 14f)
                        )
                    }
                }
            },
        ) {
            Icon(
                imageVector = Icons.Default.MyLocation,
                contentDescription = "My Location",
                tint = colorResource(R.color.blue_primary),

                )
        }

        ConfirmButton(
            enabled = pickedLocation.value != null,
            modifier = Modifier.align(Alignment.BottomCenter)
        ) {
            pickedLocation.value?.let { latLng ->
                navController.navigate("next_screen/${latLng.latitude}/${latLng.longitude}") {
                    popUpTo("location_picker") { inclusive = true }
                }
            }
        }
    }
}
