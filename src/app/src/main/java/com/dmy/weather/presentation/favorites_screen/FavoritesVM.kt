package com.dmy.weather.presentation.favorites_screen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dmy.weather.data.model.CityModel
import com.dmy.weather.data.model.LocationDetails
import com.dmy.weather.data.model.WeatherModel
import com.dmy.weather.data.repo.city_repo.CityRepository
import com.dmy.weather.data.repo.weather_repo.WeatherRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

private const val TAG = "FavoritesVM"

class FavoritesVM(
    private val cityRepository: CityRepository,
    private val weatherRepository: WeatherRepository,
) : ViewModel() {

    companion object {
        var counter = 1;
    }

    init {
        Log.i(TAG, "FavoritesVM: ${counter++}")
    }

    private val _uiState = MutableStateFlow(FavoritesUiState())
    var uiState: StateFlow<FavoritesUiState> = _uiState
        private set

    fun loadFavoriteCities() {
        _uiState.update { it.copy(isLoading = true, error = null) }
        viewModelScope.launch {
            val favResult = cityRepository.getAllFav()
            favResult.fold(
                onSuccess = { it: Flow<List<CityModel>> ->
                    it.distinctUntilChanged()
                        .collect { favCities ->
                            _uiState.update { FavoritesUiState(data = favCities) }
                        }
                },
                onFailure = { error ->
                    _uiState.update {
                        it.copy(
                            error = error.message ?: "Unknown Error",
                            isLoading = false,
                        )
                    }
                }
            )
        }
    }

    fun getWeather(long: String, lat: String, onWeatherLoaded: (weather: WeatherModel?) -> Unit) {
        viewModelScope.launch {
            weatherRepository.getWeather(
                LocationDetails(long = long, lat = lat)
            ).fold(
                onSuccess = { onWeatherLoaded(it) },
                onFailure = { onWeatherLoaded(null) }
            )
        }
    }

    fun removeFromFav(city: CityModel) {
        viewModelScope.launch {
            delay(400)
            cityRepository.removeFav(city)
        }
    }
}