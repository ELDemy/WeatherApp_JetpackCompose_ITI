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
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.dmy.weather.presentation.app_bar.AppbarViewModel
import com.dmy.weather.presentation.components.AlertDialogForLocationPermission
import com.dmy.weather.presentation.components.AlertDialogForLocationSettings
import com.dmy.weather.presentation.components.MyLoadingComponent
import com.dmy.weather.presentation.home_screen.components.NoLocationScreen
import com.dmy.weather.presentation.location_picker_screen.component.getUserLocation
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
    val state by viewModel.state.collectAsStateWithLifecycle()
    var requestLocation by remember { mutableStateOf(false) }
    val showLocationDialog = remember { mutableStateOf(false) }
    val showLocationPermissionDialog = remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        viewModel.effect.collect { effect ->
            Log.i(TAG, "HomeScreen: $effect")
            when (effect) {
                is HomeEffect.RequestGpsLocation -> requestLocation = true
                is HomeEffect.OpenLocationSettings -> showLocationDialog.value = true
                is HomeEffect.ShowWarning ->
                    snackbarHostState.showSnackbar(
                        message = effect.message,
                        duration = SnackbarDuration.Long
                    )

                is HomeEffect.OpenAppSettings -> showLocationPermissionDialog.value = true

            }
        }
    }

    when (val currentState = state) {
        is HomeState.Loading -> {
            Column(
                Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                MyLoadingComponent()
            }
        }

        is HomeState.NoLocation -> NoLocationScreen(
            onRetry = { viewModel.retry() }
        )

        is HomeState.LocationReady -> {
            WeatherScreen(
                navController = navController,
                appbarViewModel = appbarViewModel,
                location = currentState.location,
                modifier = modifier
            )
        }
    }

    if (requestLocation) {
        getUserLocation { result ->
            requestLocation = false
            viewModel.onLocationResult(result)
        }
    }
    if (showLocationDialog.value) {
        AlertDialogForLocationSettings(showLocationDialog)
    }

    if (showLocationPermissionDialog.value) {
        AlertDialogForLocationPermission(showLocationPermissionDialog)
    }
}


