package com.dmy.weather.presentation.weather_details_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dmy.weather.data.model.LocationDetails
import com.dmy.weather.data.repo.SettingsRepository
import com.dmy.weather.data.repo.WeatherRepository

@Suppress("UNCHECKED_CAST")
class WeatherVMFactory(
//    private val context: Context,
    val locationDetails: LocationDetails,
    private val weatherRepository: WeatherRepository,
    private val settingsRepository: SettingsRepository,
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
//        val repo = WeatherRepository(WeatherRemoteDataSource(), GeocodingRemoteDataSource(context))

        return WeatherVM(
            weatherRepository,
            settingsRepository,
            locationDetails
        ) as T
    }
}