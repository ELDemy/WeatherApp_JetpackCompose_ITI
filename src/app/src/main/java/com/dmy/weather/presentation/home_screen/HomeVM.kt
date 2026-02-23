package com.dmy.weather.presentation.home_screen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dmy.weather.data.enums.LocationMode
import com.dmy.weather.data.enums.LocationMode.*
import com.dmy.weather.data.model.LocationDetails
import com.dmy.weather.data.repo.SettingsRepository
import com.dmy.weather.presentation.home_screen.*
import com.dmy.weather.presentation.location_picker_screen.component.LocationResult
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

private const val TAG = "HomeVM"

class HomeVM(val settingsRepository: SettingsRepository) : ViewModel() {

    private val _state = MutableStateFlow<HomeState>(HomeState.Loading)
    val state: StateFlow<HomeState> = _state.asStateFlow()

    private val _effect = Channel<HomeEffect>(Channel.BUFFERED)
    val effect = _effect.receiveAsFlow()

    init {
        viewModelScope.launch { resolveLocation() }

        viewModelScope.launch {
            settingsRepository.settingsFlow
                .distinctUntilChanged()
                .collect {
                    Log.i(TAG, "settings changed: $it")
                    resolveLocation()
                }
        }
    }

    fun retry() {
        _state.update { HomeState.Loading }
        viewModelScope.launch { resolveLocation() }
    }

    private suspend fun getLocationMode(): LocationMode {
        return settingsRepository.getLocationMode()
    }

    private suspend fun resolveLocation() {
        when (getLocationMode()) {
            GPS -> _effect.send(HomeEffect.RequestGpsLocation)
            MAP -> {
                val location = null// settingsRepository.getSavedLocation()
                _state.update {
                    if (location != null) HomeState.CurrentLocationReady(location)
                    else {
                        _effect.send(HomeEffect.GetLocationFromMap)
                        HomeState.NoLocation
                    }
                }
            }
        }
    }

    fun onMapLocationReceived(location: LocationDetails) {
        _state.update { HomeState.CustomLocationReady(location) }
    }


    fun onLocationResult(result: LocationResult) {
        when (result) {
            is LocationResult.Current -> {
                _state.update { HomeState.CurrentLocationReady(result.latLng.toLocationDetails()) }
            }

            is LocationResult.LastKnown -> {
                _state.update { HomeState.CurrentLocationReady(result.latLng.toLocationDetails()) }
                viewModelScope.launch {
                    _effect.send(HomeEffect.ShowWarning("GPS is off. Showing last known location."))
                }
            }

            is LocationResult.Unavailable -> {
                _state.update { HomeState.NoLocation }
            }

            is LocationResult.LocationServicesOff -> {
                _state.update { HomeState.NoLocation }
                viewModelScope.launch {
                    _effect.send(HomeEffect.OpenLocationSettings)
                }
            }

            is LocationResult.PermissionDenied -> {
                _state.update { HomeState.NoLocation }
                viewModelScope.launch {
                    _effect.send(HomeEffect.ShowWarning("Location permission is required."))
                }
            }

            is LocationResult.PermissionPermanentlyDenied -> {
                _state.update { HomeState.NoLocation }
                viewModelScope.launch {
                    _effect.send(HomeEffect.OpenAppSettings)
                }
            }
        }
    }


    private fun LatLng.toLocationDetails() = LocationDetails(
        lat = latitude.toString(),
        long = longitude.toString()
    )
}

