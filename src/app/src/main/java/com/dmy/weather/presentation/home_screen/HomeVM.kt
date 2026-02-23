package com.dmy.weather.presentation.home_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dmy.weather.data.enums.LocationMode
import com.dmy.weather.data.enums.LocationMode.*
import com.dmy.weather.data.model.LocationDetails
import com.dmy.weather.data.repo.SettingsRepository
import com.dmy.weather.presentation.location_picker_screen.component.LocationResult
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeVM(val settingsRepository: SettingsRepository) : ViewModel() {

    private val _state = MutableStateFlow<HomeState>(HomeState.Loading)
    val state: StateFlow<HomeState> = _state.asStateFlow()

    private val _effect = Channel<HomeEffect>(Channel.BUFFERED)
    val effect = _effect.receiveAsFlow()

    init {
        viewModelScope.launch { resolveLocation() }
    }

    fun retry() {
        _state.update { HomeState.Loading }
        viewModelScope.launch { resolveLocation() }
    }


    private suspend fun resolveLocation() {
        when (getLocationMode()) {
            GPS -> _effect.send(HomeEffect.RequestGpsLocation)
            MAP -> {
                val location = null// settingsRepository.getSavedLocation()
                _state.update {
                    if (location != null) HomeState.LocationReady(location)
                    else HomeState.NoLocation
                }
            }
        }
    }

    private suspend fun getLocationMode(): LocationMode {
        return settingsRepository.getLocationMode()
    }

    fun onLocationResult(result: LocationResult) {
        when (result) {
            is LocationResult.Current -> {
                _state.update { HomeState.LocationReady(result.latLng.toLocationDetails()) }
            }

            is LocationResult.LastKnown -> {
                _state.update { HomeState.LocationReady(result.latLng.toLocationDetails()) }
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
        }
    }


    private fun LatLng.toLocationDetails() = LocationDetails(
        lat = latitude.toString(),
        long = longitude.toString()
    )
}

