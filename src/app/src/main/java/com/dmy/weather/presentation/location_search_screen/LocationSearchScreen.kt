package com.dmy.weather.presentation.location_search_screen

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
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.dmy.weather.R
import com.dmy.weather.data.mapper.toLatLng
import com.dmy.weather.data.model.LocationDetails
import com.dmy.weather.presentation.location_search_screen.component.ConfirmButton
import com.dmy.weather.presentation.location_search_screen.component.search_field.MapsSearchField
import com.dmy.weather.presentation.my_app.NavScreens
import com.dmy.weather.presentation.permissions.location.LocationResult
import com.dmy.weather.presentation.permissions.location.getUserLocation
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
fun LocationSearchScreen(
    navController: NavController,
    modifier: Modifier,
    initialLocation: LocationDetails?,
    popOnLocationPicked: String
) {
    val pickedLocation = remember { mutableStateOf<LatLng?>(null) }
    val showSuggestions = remember { mutableStateOf(false) }
    var mapLoaded by remember { mutableStateOf(false) }
    var userLatLng by remember { mutableStateOf<LatLng?>(null) }

    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(
            initialLocation.toLatLng() ?: LatLng(30.0444, 31.2357),
            9f
        )
    }

    getUserLocation {
        if (it is LocationResult.Current) {
            userLatLng = it.latLng

            if (initialLocation == null)
                pickedLocation.value = it.latLng
        }
    }

    LaunchedEffect(mapLoaded) {
        if (initialLocation != null) {
            pickedLocation.value = initialLocation.toLatLng()
        }
    }

    LaunchedEffect(mapLoaded, pickedLocation.value) {
        if (mapLoaded && pickedLocation.value != null) {
            cameraPositionState.animate(
                CameraUpdateFactory.newLatLngZoom(pickedLocation.value!!, 10f)
            )
        }
    }

    Box(modifier = modifier.fillMaxSize()) {
        GoogleMap(
            modifier = Modifier.fillMaxSize(),
            cameraPositionState = cameraPositionState,
            properties = MapProperties(isMyLocationEnabled = userLatLng != null),
            uiSettings = MapUiSettings(myLocationButtonEnabled = false),
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
            MapsSearchField(modifier = Modifier.align(Alignment.TopCenter)) {
                pickedLocation.value =
                    LocationDetails(
                        long = it.longitude.toString(),
                        lat = it.latitude.toString()
                    ).toLatLng()
            }

            if (userLatLng != null)
                FloatingActionButton(
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .padding(end = 16.dp, bottom = 115.dp)
                        .size(48.dp),
                    containerColor = colorResource(R.color.white),
                    onClick = {
                        userLatLng?.let {
                            if (mapLoaded) {
                                pickedLocation.value = userLatLng
                            }
                        }
                    },
                ) {
                    Icon(
                        imageVector = Icons.Default.MyLocation,
                        contentDescription = "My Location",
                        tint = colorResource(R.color.blue_primary),
                        modifier = Modifier.size(32.dp)
                    )
                }

            ConfirmButton(
                enabled = pickedLocation.value != null,
                modifier = Modifier.align(Alignment.BottomCenter)
            ) {
                pickedLocation.value?.let { latLng ->
                    if (popOnLocationPicked == "1") {
                        navController.previousBackStackEntry
                            ?.savedStateHandle
                            ?.set(
                                "picked_location",
                                LocationDetails(
                                    lat = latLng.latitude.toString(),
                                    long = latLng.longitude.toString()
                                )
                            )
                        navController.popBackStack()
                    } else {
                        navController.navigate(
                            NavScreens.WeatherScreen(
                                long = latLng.longitude.toString(),
                                lat = latLng.latitude.toString(),
                            )
                        )
                    }
                }
            }
        }
    }
}
