package com.dmy.weather.data.data_source.remote.weather_data_source

import com.dmy.weather.data.dto.DailyForecastDTO
import com.dmy.weather.data.dto.HourlyForecastDTO
import com.dmy.weather.data.dto.WeatherDTO
import com.dmy.weather.data.model.LocationDetails

interface WeatherRemoteDataSource {
    suspend fun getCurrentWeather(city: String): WeatherDTO?

    suspend fun getCurrentWeather(long: String, lat: String): WeatherDTO?
    
    suspend fun getDailyForecast(city: String): DailyForecastDTO?

    suspend fun getDailyForecast(long: String, lat: String): DailyForecastDTO?

    suspend fun getClimateForecast(city: String): DailyForecastDTO?

    suspend fun getClimateForecast(long: String, lat: String): DailyForecastDTO?

    suspend fun getHourlyForecast(locationDetails: LocationDetails?): HourlyForecastDTO?

    suspend fun getHourlyForecast(city: String): HourlyForecastDTO?

    suspend fun getHourlyForecast(long: String, lat: String): HourlyForecastDTO?
}