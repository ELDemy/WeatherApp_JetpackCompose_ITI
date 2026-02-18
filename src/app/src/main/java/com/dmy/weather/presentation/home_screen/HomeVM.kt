package com.dmy.weather.presentation.home_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dmy.weather.data.repo.WeatherRepo
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeVM(val weatherRepo: WeatherRepo) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState

    init {
        loadWeatherData()
    }

    companion object {
        private const val TAG = "HomeViewModel"
    }

    fun loadWeatherData() {
        viewModelScope.launch(SupervisorJob()) {
            launch { loadCurrentWeather() }
//            launch { loadHourlyForecast() }
        }
    }

    private suspend fun loadCurrentWeather() {
        _uiState.update { it.copy(currentWeather = UiState(isLoading = true)) }

        val result = weatherRepo.getCurrentWeather("Cairo")

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

        val result = weatherRepo.getHourlyForecast("Cairo")

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


}