package com.dmy.weather.presentation.weather_details_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dmy.weather.data.repo.city_repo.CityRepository
import com.dmy.weather.data.repo.settings_repo.SettingsRepository
import com.dmy.weather.data.repo.weather_repo.WeatherRepository

@Suppress("UNCHECKED_CAST")
class WeatherVMFactory(
    private val weatherRepository: WeatherRepository,
    private val cityRepository: CityRepository,
    private val settingsRepository: SettingsRepository,
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
//        val repo = WeatherRepository(WeatherRemoteDataSource(), GeocodingRemoteDataSource(context))

        return WeatherVM(
            weatherRepository,
            cityRepository,
            settingsRepository,
        ) as T
    }
}