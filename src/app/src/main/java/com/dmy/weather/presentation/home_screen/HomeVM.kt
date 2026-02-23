package com.dmy.weather.presentation.home_screen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dmy.weather.data.enums.LocationMode
import com.dmy.weather.data.enums.LocationMode.GPS
import com.dmy.weather.data.enums.LocationMode.MAP
import com.dmy.weather.data.model.LocationDetails
import com.dmy.weather.data.model.toLocationDetails
import com.dmy.weather.data.repo.SettingsRepository
import com.dmy.weather.presentation.location_picker_screen.component.LocationResult
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

    private val _uiState = MutableStateFlow<HomeUiState>(HomeUiState.Loading)
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    private val _effect = Channel<HomeEffect>(Channel.BUFFERED)
    val effect = _effect.receiveAsFlow()

    private var activeLocation: LocationDetails? = null

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
        _uiState.update { HomeUiState.Loading }
        viewModelScope.launch { resolveLocation() }
    }

    private suspend fun resolveLocation() {
        when (getLocationMode()) {
            GPS -> _effect.send(HomeEffect.RequestGpsLocation)
            MAP -> {
                val location = getDefaultLocation()
                activeLocation = location
                _uiState.update {
                    if (location != null) HomeUiState.CustomLocationReady(location)
                    else {
                        val location = getLastKnownLocation()
                        openMap()
                        if (location != null) {
                            HomeUiState.CurrentLocationReady(location)
                        } else {
                            HomeUiState.OldLocation()
                        }
                    }
                }
            }
        }
    }

    private suspend fun getLocationMode(): LocationMode {
        return settingsRepository.getLocationMode()
    }

    private suspend fun getDefaultLocation(): LocationDetails? {
        return settingsRepository.getDefaultLocation()
    }

    private fun saveDefaultLocation(locationDetails: LocationDetails) {
        activeLocation = locationDetails
        viewModelScope.launch {
            settingsRepository.saveDefaultLocation(locationDetails)
        }
    }

    private fun saveLastKnownLocation(locationDetails: LocationDetails) {
        activeLocation = locationDetails
        viewModelScope.launch {
            settingsRepository.saveLastKnownLocation(locationDetails)
        }
    }

    private suspend fun getLastKnownLocation(): LocationDetails? {
        return settingsRepository.getLastKnownLocation()
    }

    fun openMap() {
        viewModelScope.launch {
            _effect.send(HomeEffect.GetLocationFromMap(activeLocation))
        }
    }

    fun onMapLocationReceived(location: LocationDetails) {
        saveDefaultLocation(location)
        this.activeLocation = location
        _uiState.update { HomeUiState.CustomLocationReady(location) }
    }

    fun onLocationResult(result: LocationResult) {
        Log.i(TAG, "onLocationResult: $result")
        when (result) {
            is LocationResult.Current -> {
                val locationDetails = result.latLng.toLocationDetails()
                activeLocation = locationDetails
                saveLastKnownLocation(locationDetails)
                _uiState.update { HomeUiState.CurrentLocationReady(locationDetails) }
            }

            is LocationResult.LastKnown -> {
                val locationDetails = result.latLng.toLocationDetails()
                activeLocation = locationDetails
                _uiState.update { HomeUiState.CurrentLocationReady(locationDetails) }

                viewModelScope.launch {
                    _effect.send(HomeEffect.ShowWarning("GPS is off. Showing last known location."))
                }
            }

            else -> {
                viewModelScope.launch {
                    val oldLocation = getLastKnownLocation() ?: getDefaultLocation()
                    activeLocation = oldLocation
                    when (result) {
                        is LocationResult.Unavailable -> {
                            _uiState.update {
                                HomeUiState.OldLocation(
                                    oldLocation,
                                    warning = "Location is not available.",
                                    effect = HomeEffect.RequestGpsLocation
                                )
                            }
                        }

                        is LocationResult.LocationServicesOff -> {
                            _uiState.update {
                                HomeUiState.OldLocation(
                                    oldLocation,
                                    warning = "Location services are off please enable it.",
                                    effect = HomeEffect.OpenLocationSettings
                                )
                            }
                            viewModelScope.launch {
                                _effect.send(HomeEffect.OpenLocationSettings)
                            }
                        }

                        is LocationResult.PermissionDenied -> {
                            _uiState.update {
                                HomeUiState.OldLocation(
                                    oldLocation,
                                    warning = "Location permission is required.",
                                    effect = HomeEffect.RequestGpsLocation
                                )
                            }

                            viewModelScope.launch {
                                _effect.send(
                                    HomeEffect.ShowWarning("Location permission is required.")
                                )
                            }
                        }

                        is LocationResult.PermissionPermanentlyDenied -> {
                            _uiState.update {
                                HomeUiState.OldLocation(
                                    oldLocation,
                                    warning = "Location permission is required.",
                                    effect = HomeEffect.OpenAppSettings
                                )
                            }

                            viewModelScope.launch {
                                _effect.send(HomeEffect.OpenAppSettings)
                            }
                        }
                    }
                }
            }
        }
    }


}

