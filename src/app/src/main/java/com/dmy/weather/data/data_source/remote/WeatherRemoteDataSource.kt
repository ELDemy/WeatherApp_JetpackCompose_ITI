package com.dmy.weather.data.data_source.remote

import com.dmy.weather.data.dto.WeatherDTO
import com.dmy.weather.data.network.WeatherNetwork
import com.dmy.weather.data.network.WeatherService

class WeatherRemoteDataSource {
    companion object {
        private const val TAG = "WeatherRemoteDataSource"
        private val weatherService: WeatherService = WeatherNetwork.weatherService
    }

    suspend fun getCurrentWeather(city: String): WeatherDTO? {
        return weatherService.getCurrentWeather(city)
    }

}