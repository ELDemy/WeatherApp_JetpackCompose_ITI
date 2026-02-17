package com.dmy.weather.data.repo

import android.util.Log
import com.dmy.weather.data.data_source.remote.WeatherRemoteDataSource
import com.dmy.weather.data.mapper.toModel

class WeatherRepo {
    companion object {
        private const val TAG = "WeatherRepo"
        private val weatherRemoteDataSource = WeatherRemoteDataSource()
    }

    suspend fun getCurrentWeather(city: String) {
        val weatherDTO = weatherRemoteDataSource.getCurrentWeather(city)
        val weatherModel = weatherDTO?.toModel()

        Log.i(TAG, "weatherDTO: $weatherDTO")
        Log.i(TAG, "weatherModel: $weatherModel")

    }

    suspend fun getCurrentWeather(long: String, lat: String) {
        val weatherDTO = weatherRemoteDataSource.getCurrentWeather(long, lat)
        val weatherModel = weatherDTO?.toModel()

        Log.i(TAG, "weatherDTO: $weatherDTO")
        Log.i(TAG, "weatherModel: $weatherModel")

    }

    suspend fun getDailyForecast(city: String) {
        val dailyForecastDTO = weatherRemoteDataSource.getDailyForecast(city)
        val dailyForecastModel = dailyForecastDTO?.toModel()

        Log.i(TAG, "dailyForecastDTO: $dailyForecastDTO")
        Log.i(TAG, "weatherModel: $dailyForecastModel")

    }

    suspend fun getDailyForecast(long: String, lat: String) {
        val dailyForecastDTO = weatherRemoteDataSource.getDailyForecast(long, lat)
        val dailyForecastModel = dailyForecastDTO?.toModel()

        Log.i(TAG, "dailyForecastDTO: $dailyForecastDTO")
        Log.i(TAG, "weatherModel: $dailyForecastModel")

    }

    suspend fun getClimateForecast(city: String) {
        val dailyForecastDTO = weatherRemoteDataSource.getClimateForecast(city)
        val dailyForecastModel = dailyForecastDTO?.toModel()

        Log.i(TAG, "dailyForecastDTO: $dailyForecastDTO")
        Log.i(TAG, "weatherModel: $dailyForecastModel")
    }

    suspend fun getClimateForecast(long: String, lat: String) {
        val dailyForecastDTO = weatherRemoteDataSource.getClimateForecast(long, lat)
        val dailyForecastModel = dailyForecastDTO?.toModel()

        Log.i(TAG, "dailyForecastDTO: $dailyForecastDTO")
        Log.i(TAG, "weatherModel: $dailyForecastModel")
    }

    suspend fun getHourlyForecast(city: String) {
        val hourlyForecastDTO = weatherRemoteDataSource.getHourlyForecast(city)
        val hourlyForecastModel = hourlyForecastDTO?.toModel()

        Log.i(TAG, "hourlyForecastDTO: $hourlyForecastDTO")
        Log.i(TAG, "hourlyForecastModel: $hourlyForecastModel")
    }

    suspend fun getHourlyForecast(long: String, lat: String) {
        val hourlyForecastDTO = weatherRemoteDataSource.getHourlyForecast(long, lat)
        val hourlyForecastModel = hourlyForecastDTO?.toModel()

        Log.i(TAG, "hourlyForecastDTO: $hourlyForecastDTO")
        Log.i(TAG, "hourlyForecastModel: $hourlyForecastModel")
    }
}