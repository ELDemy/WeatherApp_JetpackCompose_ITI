package com.dmy.weather.presentation.home_screen

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dmy.weather.R
import com.dmy.weather.data.enums.LocationMode
import com.dmy.weather.data.mapper.toLocationDetails
import com.dmy.weather.data.model.LocationDetails
import com.dmy.weather.data.repo.settings_repo.SettingsRepository
import com.dmy.weather.presentation.permissions.location.LocationResult
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

private const val TAG = "HomeVM"

class HomeVM(val settingsRepository: SettingsRepository) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    private val _effect = MutableSharedFlow<HomeEffect>()
    val effect = _effect.asSharedFlow()

    private var activeLocation: LocationDetails? = null

    init {
        viewModelScope.launch { resolveLocation() }

        viewModelScope.launch {
            settingsRepository.settingsFlow
                .distinctUntilChanged()
                .collect {
                    Log.i(TAG, "settings changed in HomeVM: $it")
                    resolveLocation()
                }
        }
    }

    fun retry() {
        _uiState.update { it.copy(isRefreshing = true) }
        viewModelScope.launch { resolveLocation() }
    }

    private suspend fun resolveLocation() {
        Log.i(TAG, "resolveLocation: ")
        when (getLocationMode()) {
            LocationMode.GPS -> {
                _uiState.update {
                    it.copy(
                        locationMode = LocationMode.GPS,
                        isLoadingGPSLocation = true
                    )
                }
            }

            LocationMode.MAP -> {
                _uiState.update { it.copy(locationMode = LocationMode.MAP) }
                val location = getDefaultLocation()
                if (location != null) {
                    activeLocation = location
                    _uiState.update {
                        it.copy(
                            location = location,
                            isLoading = false,
                            isRefreshing = false,
                            noLocation = false
                        )
                    }
                } else {
                    val lastKnown = getLastKnownLocation()
                    openMap()
                    _uiState.update {
                        it.copy(
                            location = lastKnown,
                            isLoading = false,
                            isRefreshing = false,
                            noLocation = lastKnown == null
                        )
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
            _effect.emit(HomeEffect.GetLocationFromMap(activeLocation))
        }
    }

    fun onMapLocationReceived(location: LocationDetails) {
        saveDefaultLocation(location)
        this.activeLocation = location
        _uiState.update {
            it.copy(
                location = location,
                locationMode = LocationMode.MAP,
                isLoading = false,
                isRefreshing = false,
                warning = null,
                warningEffect = null,
                noLocation = false
            )

        }
    }

    fun onLocationResult(result: LocationResult, context: Context) {
        Log.i(TAG, "onLocationResult: $result")
        _uiState.update { it.copy(isLoadingGPSLocation = false) }
        when (result) {
            is LocationResult.Current -> {
                val locationDetails = result.latLng.toLocationDetails()
                activeLocation = locationDetails
                saveLastKnownLocation(locationDetails)
                _uiState.update {
                    it.copy(
                        location = locationDetails,
                        locationMode = LocationMode.GPS,
                        isLoading = false,
                        isRefreshing = false,
                        warning = null,
                        warningEffect = null,
                        noLocation = false
                    )
                }
            }

            is LocationResult.LastKnown -> {
                val locationDetails = result.latLng.toLocationDetails()
                activeLocation = locationDetails
                _uiState.update {
                    it.copy(
                        location = locationDetails,
                        locationMode = LocationMode.GPS,
                        warning = context.getString(R.string.GPS_is_off),
                        warningEffect = HomeEffect.OpenLocationSettings,
                        isLoading = false,
                        isRefreshing = false,
                        noLocation = false
                    )
                }
                viewModelScope.launch {
                    _effect.emit(HomeEffect.ShowWarning(context.getString(R.string.GPS_is_off)))
                }
            }

            is LocationResult.LocationServicesOff -> {
                viewModelScope.launch {
                    val oldLocation = getLastKnownLocation() ?: getDefaultLocation()
                    activeLocation = oldLocation
                    _uiState.update {
                        it.copy(
                            location = oldLocation,
                            isLoading = false,
                            isRefreshing = false,
                            warning = context.getString(R.string.Location_services_are_off),
                            warningEffect = HomeEffect.OpenLocationSettings,
                            noLocation = oldLocation == null
                        )
                    }
                    _effect.emit(HomeEffect.OpenLocationSettings)
                }
            }

            is LocationResult.PermissionDenied -> {
                viewModelScope.launch {
                    val oldLocation = getLastKnownLocation() ?: getDefaultLocation()
                    activeLocation = oldLocation
                    _uiState.update {
                        it.copy(
                            location = oldLocation,
                            isLoading = false,
                            isRefreshing = false,
                            warning = context.getString(R.string.Location_permission_is_required),
                            warningEffect = HomeEffect.RequestGpsLocation,
                            noLocation = oldLocation == null
                        )
                    }
                    _effect.emit(HomeEffect.ShowWarning(context.getString(R.string.Location_permission_is_required)))
                }
            }

            is LocationResult.PermissionPermanentlyDenied -> {
                viewModelScope.launch {
                    val oldLocation = getLastKnownLocation() ?: getDefaultLocation()
                    activeLocation = oldLocation
                    _uiState.update {
                        it.copy(
                            location = oldLocation,
                            isLoading = false,
                            isRefreshing = false,
                            warning = context.getString(R.string.Location_permission_is_permanently_denied_),
                            warningEffect = HomeEffect.OpenAppSettings,
                            noLocation = oldLocation == null
                        )
                    }
                    _effect.emit(HomeEffect.OpenAppSettings)
                }
            }

            is LocationResult.Unavailable -> {
                viewModelScope.launch {
                    val oldLocation = getLastKnownLocation() ?: getDefaultLocation()
                    activeLocation = oldLocation
                    _uiState.update {
                        it.copy(
                            location = oldLocation,
                            isLoading = false,
                            isRefreshing = false,
                            warning = context.getString(R.string.Location_is_not_available),
                            warningEffect = HomeEffect.RequestGpsLocation,
                            noLocation = oldLocation == null
                        )
                    }
                }
            }
        }
    }

    fun onWarningClicked() {
        viewModelScope.launch {
            when (_uiState.value.warningEffect) {
                is HomeEffect.OpenLocationSettings -> _effect.emit(HomeEffect.OpenLocationSettings)
                is HomeEffect.OpenAppSettings -> _effect.emit(HomeEffect.OpenAppSettings)
                is HomeEffect.RequestGpsLocation -> _effect.emit(HomeEffect.RequestGpsLocation)
                else -> retry()
            }
        }
    }
}

