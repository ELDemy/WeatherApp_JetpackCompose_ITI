package com.dmy.weather.data.data_source.remote

import com.dmy.weather.data.dto.DailyForecastDTO
import com.dmy.weather.data.dto.HourlyForecastDTO
import com.dmy.weather.data.dto.WeatherDTO
import com.dmy.weather.data.network.WeatherService

class WeatherRemoteDataSource(val weatherService: WeatherService) {
    companion object {
        private const val TAG = "WeatherRemoteDataSource"
    }

    suspend fun getCurrentWeather(city: String): WeatherDTO? {
        return weatherService.getCurrentWeather(city)
    }

    suspend fun getCurrentWeather(long: String, lat: String): WeatherDTO? {
        return weatherService.getCurrentWeather(long, lat)
    }

    suspend fun getDailyForecast(city: String): DailyForecastDTO? {
        return weatherService.getDailyForecast(city)
    }

    suspend fun getDailyForecast(long: String, lat: String): DailyForecastDTO? {
        return weatherService.getDailyForecast(long, lat)
    }

    suspend fun getClimateForecast(city: String): DailyForecastDTO? {
        return weatherService.getClimateForecast(city)
    }

    suspend fun getClimateForecast(long: String, lat: String): DailyForecastDTO? {
        return weatherService.getClimateForecast(long, lat)
    }

    suspend fun getHourlyForecast(city: String): HourlyForecastDTO? {
        return weatherService.getHourlyForecast(city)
    }

    suspend fun getHourlyForecast(long: String, lat: String): HourlyForecastDTO? {
        return weatherService.getHourlyForecast(long, lat)
    }


}