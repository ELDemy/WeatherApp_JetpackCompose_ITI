package com.dmy.weather.presentation.home_screen

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.dmy.weather.data.enums.LocationMode
import com.dmy.weather.data.model.LocationDetails
import com.dmy.weather.presentation.app_bar.AppbarViewModel
import com.dmy.weather.presentation.components.AlertDialogForLocationPermission
import com.dmy.weather.presentation.components.AlertDialogForLocationSettings
import com.dmy.weather.presentation.components.MyLoadingComponent
import com.dmy.weather.presentation.home_screen.components.NoLocationScreen
import com.dmy.weather.presentation.home_screen.components.WeatherScreenWithCustomLocation
import com.dmy.weather.presentation.my_app.NavScreens.LocationSearchScreen
import com.dmy.weather.presentation.permissions.location.getUserLocation
import com.dmy.weather.presentation.weather_details_screen.WeatherScreen
import org.koin.androidx.compose.koinViewModel

private const val TAG = "HomeScreen"

@Composable
fun HomeScreen(
    navController: NavController,
    appbarViewModel: AppbarViewModel,
    snackbarHostState: SnackbarHostState,
    modifier: Modifier,
    viewModel: HomeVM = koinViewModel()
) {
    val context = LocalContext.current

    val state by viewModel.uiState.collectAsStateWithLifecycle()
    val isLoadingGpsLocation = state.isLoadingGPSLocation

    val showLocationDialog = remember { mutableStateOf(false) }
    val showLocationPermissionDialog = remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        navController.currentBackStackEntry
            ?.savedStateHandle
            ?.getStateFlow<LocationDetails?>("picked_location", null)
            ?.collect { location ->
                Log.i(TAG, "HomeScreen: Location received is $location")
                if (location != null) {
                    navController.currentBackStackEntry
                        ?.savedStateHandle
                        ?.remove<LocationDetails>("picked_location")

                    viewModel.onMapLocationReceived(location)
                }
            }
    }


    LaunchedEffect(Unit) {
        viewModel.effect.collect { effect ->
            Log.i(TAG, "HomeScreen: $effect")
            when (effect) {
                is HomeEffect.OpenLocationSettings -> showLocationDialog.value = true
                is HomeEffect.OpenAppSettings -> showLocationPermissionDialog.value = true

                is HomeEffect.GetLocationFromMap -> {
                    Log.i(TAG, "HomeScreen: activeLocation ${effect.currentLocation}")
                    navController.navigate(
                        LocationSearchScreen(
                            effect.currentLocation?.long,
                            effect.currentLocation?.lat,
                            popOnLocationPicked = "1"
                        )
                    )
                }

                is HomeEffect.ShowWarning ->
                    snackbarHostState.showSnackbar(
                        message = effect.message,
                        duration = SnackbarDuration.Long
                    )

                HomeEffect.RequestGpsLocation -> {}
            }
        }
    }


    if (isLoadingGpsLocation) {
        getUserLocation { result ->
            viewModel.onLocationResult(result, context)
        }
    }

    if (showLocationDialog.value) {
        AlertDialogForLocationSettings(showLocationDialog)
    }

    if (showLocationPermissionDialog.value) {
        AlertDialogForLocationPermission(showLocationPermissionDialog)
    }

    when {
        state.isLoading && state.location == null -> {
            Column(
                Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) { MyLoadingComponent() }
        }

        state.noLocation -> {
            NoLocationScreen(
                state,
                onRetry = { viewModel.retry() }
            )
        }

        state.location != null -> {
            if (state.locationMode == LocationMode.MAP) {
                WeatherScreenWithCustomLocation(
                    modifier = modifier,
                    navController = navController,
                    snackbarHostState = snackbarHostState,
                    appbarViewModel = appbarViewModel,
                    location = state.location!!,
                    warning = state.warning,
                    isLoading = isLoadingGpsLocation,
                    onFabClick = { viewModel.openMap() },
                    onRefresh = { viewModel.retry() },
                    onWarningClick = { viewModel.onWarningClicked() }
                )
            } else {
                WeatherScreen(
                    modifier = modifier,
                    navController = navController,
                    snackbarHostState = snackbarHostState,
                    appbarViewModel = appbarViewModel,
                    location = state.location!!,
                    showFavFab = false,
                    warning = state.warning,
                    onRefresh = { viewModel.retry() },
                    onWarningClick = { viewModel.onWarningClicked() }
                )
            }
        }
    }
}


