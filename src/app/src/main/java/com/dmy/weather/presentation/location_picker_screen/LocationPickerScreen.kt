package com.dmy.weather.presentation.location_picker_screen

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
import androidx.navigation.NavController
import com.dmy.weather.R
import com.dmy.weather.presentation.location_picker_screen.component.ConfirmButton
import com.dmy.weather.presentation.location_picker_screen.component.MapsSearchField
import com.dmy.weather.presentation.location_picker_screen.component.RequestLocationPermission
import com.dmy.weather.presentation.location_picker_screen.component.getLocation
import com.dmy.weather.presentation.location_picker_screen.component.hasLocationPermission
import com.dmy.weather.presentation.my_app.NavScreens
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

@Composable
fun LocationPickerScreen(navController: NavController, modifier: Modifier) {
    val context = LocalContext.current
    val pickedLocation = remember { mutableStateOf<LatLng?>(null) }
    val showSuggestions = remember { mutableStateOf(false) }
    var hasPermission by remember { mutableStateOf(hasLocationPermission(context)) }
    var mapLoaded by remember { mutableStateOf(false) }
    var userLatLng by remember { mutableStateOf<LatLng?>(null) }

    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(userLatLng ?: LatLng(30.0444, 31.2357), 10f)
    }

    if (!hasPermission) {
        RequestLocationPermission { granted -> hasPermission = granted }
    }

    LaunchedEffect(hasPermission) {
        if (hasPermission && userLatLng == null) {
            val location = getLocation(context)
            location?.let {
                userLatLng = it
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

    Box(modifier = modifier.fillMaxSize()) {
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

        if (mapLoaded) {
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
                    navController.navigate(
                        NavScreens.WeatherScreen(
                            long = latLng.longitude.toString(),
                            lat = latLng.latitude.toString()
                        )
                    ) {
                        popUpTo(NavScreens.LocationPickerScreen) { inclusive = true }
                    }
                }
            }
        }
    }
}
