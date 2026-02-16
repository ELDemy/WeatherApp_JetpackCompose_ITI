package com.dmy.weather.data.repo

import android.util.Log
import com.dmy.weather.data.data_source.remote.WeatherRemoteDataSource
import com.dmy.weather.mapper.toModel
import kotlinx.coroutines.runBlocking

class WeatherRepo {

    companion object {
        private const val TAG = "WeatherRepo"
        private val weatherRemoteDataSource = WeatherRemoteDataSource()
    }

    fun getCurrentWeatherInCity(city: String) {
        runBlocking {
            val weatherDTO = weatherRemoteDataSource.getCurrentWeather(city)
            val weatherModel = weatherDTO?.toModel()
            
            Log.i(TAG, "weatherDTO: $weatherDTO")
            Log.i(TAG, "weatherModel: $weatherModel")
        }
    }
}