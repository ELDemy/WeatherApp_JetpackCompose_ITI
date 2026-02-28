package com.dmy.weather.presentation.weather_details_screen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dmy.weather.data.enums.AppLanguage
import com.dmy.weather.data.enums.LocationMode
import com.dmy.weather.data.enums.UnitSystem
import com.dmy.weather.data.model.LocationDetails
import com.dmy.weather.data.model.UserSettings
import com.dmy.weather.data.repo.city_repo.CityRepository
import com.dmy.weather.data.repo.settings_repo.SettingsRepository
import com.dmy.weather.data.repo.weather_repo.WeatherRepository
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.drop
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class WeatherVM(
    val weatherRepository: WeatherRepository,
    val cityRepository: CityRepository,
    val settingsRepository: SettingsRepository,
) : ViewModel() {

    companion object {
        private const val TAG = "WeatherVM"
        var counter = 1
    }

    lateinit var locationDetails: LocationDetails
    private val _uiState = MutableStateFlow(WeatherUiState())
    val uiState: StateFlow<WeatherUiState> = _uiState

    init {
        Log.i(TAG, "WeatherVM counter is : ${counter++}")

        viewModelScope.launch {
            settingsRepository.settingsFlow
                .drop(1)
                .distinctUntilChanged()
                .collect {
                    Log.i(
                        TAG,
                        "settings changed in WeatherVM: $it"
                    )

                    if (::locationDetails.isInitialized) {
                        loadWeatherData(locationDetails)
                    }
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


    fun loadWeatherData(locationDetails: LocationDetails) {
        this.locationDetails = locationDetails
        viewModelScope.launch(SupervisorJob()) {
            launch { loadCurrentWeather() }
            launch { loadHourlyForecast() }
            launch { loadDailyForecast() }
        }
    }

    private suspend fun loadCurrentWeather() {
        _uiState.update { it.copy(currentWeather = it.currentWeather.copy(isLoading = true)) }

        val result = weatherRepository.getWeather(locationDetails)

        _uiState.update {
            it.copy(
                currentWeather = result.fold(
                    onSuccess = { model ->
                        it.currentWeather.copy(
                            isLoading = false,
                            data = model,
                            error = null
                        )
                    },
                    onFailure = { error ->
                        it.currentWeather.copy(
                            isLoading = false,
                            error = error.message ?: "Unknown Error"
                        )
                    }
                )
            )
        }
    }

    private suspend fun loadHourlyForecast() {
        _uiState.update { it.copy(hourlyForecast = it.hourlyForecast.copy(isLoading = true)) }

        val result = weatherRepository.getHourlyForecast(locationDetails)

        _uiState.update {
            it.copy(
                hourlyForecast = result.fold(
                    onSuccess = { model ->
                        it.hourlyForecast.copy(isLoading = false, data = model, error = null)
                    },
                    onFailure = { error ->
                        it.hourlyForecast.copy(
                            isLoading = false,
                            error = error.message ?: "Unknown Error"
                        )
                    }
                )
            )
        }
    }

    private suspend fun loadDailyForecast() {
        _uiState.update { it.copy(dailyForecast = it.dailyForecast.copy(isLoading = true)) }

        val result = weatherRepository.getDailyForecast(locationDetails)

        _uiState.update {
            it.copy(
                dailyForecast = result.fold(
                    onSuccess = { model ->
                        it.dailyForecast.copy(isLoading = false, data = model, error = null)
                    },
                    onFailure = { error ->
                        it.dailyForecast.copy(
                            isLoading = false,
                            error = error.message ?: "Unknown Error"
                        )
                    }
                )
            )
        }
    }

    fun addToFav(cityName: String) {
        viewModelScope.launch {
            if (::locationDetails.isInitialized) {
                cityRepository.addFav(locationDetails.copy(city = cityName))
            }
        }
    }

}