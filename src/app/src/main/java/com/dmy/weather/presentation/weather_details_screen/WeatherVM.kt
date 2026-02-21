package com.dmy.weather.presentation.weather_details_screen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dmy.weather.data.enums.AppLanguage
import com.dmy.weather.data.enums.LocationMode
import com.dmy.weather.data.enums.UnitSystem
import com.dmy.weather.data.model.LocationDetails
import com.dmy.weather.data.model.UserSettings
import com.dmy.weather.data.repo.SettingsRepository
import com.dmy.weather.data.repo.WeatherRepository
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class WeatherVM(
    val weatherRepository: WeatherRepository,
    val settingsRepository: SettingsRepository,
    val locationDetails: LocationDetails
) : ViewModel() {

    companion object {
        private const val TAG = "HomeViewModel"
        var counter = 1
    }

    private val _uiState = MutableStateFlow(WeatherUiState())
    val uiState: StateFlow<WeatherUiState> = _uiState

    init {
        Log.i(TAG, "WeatherVM counter is : ${counter++}")
        loadWeatherData()

        viewModelScope.launch {
            settingsRepository.settingsFlow
                .distinctUntilChanged()
                .collect {
                    loadWeatherData()
                }
        }
    }

    val settingsState = settingsRepository.settingsFlow.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = UserSettings(
            lang = AppLanguage.DEFAULT,
            unit = UnitSystem.METRIC,
            locationMode = LocationMode.GPS
        )
    )


    fun loadWeatherData() {
        viewModelScope.launch(SupervisorJob()) {
            launch { loadCurrentWeather() }
            launch { loadHourlyForecast() }
            launch { loadDailyForecast() }
        }
    }

    private suspend fun loadCurrentWeather() {
        _uiState.update { it.copy(currentWeather = UiState(isLoading = true)) }

        val result = weatherRepository.getCurrentWeather(locationDetails)

        _uiState.update {
            it.copy(
                currentWeather = result.fold(
                    onSuccess = { model -> UiState(isLoading = false, data = model) },
                    onFailure = { error ->
                        UiState(
                            isLoading = false,
                            error = error.message ?: "Unknown Error"
                        )
                    }
                )
            )
        }
    }

    private suspend fun loadHourlyForecast() {
        _uiState.update { it.copy(hourlyForecast = UiState(isLoading = true)) }

        val result = weatherRepository.getHourlyForecast(locationDetails)

        _uiState.update {
            it.copy(
                hourlyForecast = result.fold(
                    onSuccess = { model ->
                        UiState(isLoading = false, data = model)
                    },
                    onFailure = { error ->
                        UiState(
                            isLoading = false,
                            error = error.message ?: "Unknown Error"
                        )
                    }
                )
            )
        }
    }

    private suspend fun loadDailyForecast() {
        _uiState.update { it.copy(dailyForecast = UiState(isLoading = true)) }

        val result = weatherRepository.getDailyForecast(locationDetails)

        _uiState.update {
            it.copy(
                dailyForecast = result.fold(
                    onSuccess = { model ->
                        UiState(isLoading = false, data = model)
                    },
                    onFailure = { error ->
                        UiState(
                            isLoading = false,
                            error = error.message ?: "Unknown Error"
                        )
                    }
                )
            )
        }
    }


}