package com.dmy.weather.data.data_source.remote.weather_data_source

import com.dmy.weather.data.dto.DailyForecastDTO
import com.dmy.weather.data.dto.HourlyForecastDTO
import com.dmy.weather.data.dto.WeatherDTO
import com.dmy.weather.data.model.LocationDetails

interface WeatherRemoteDataSource {
    suspend fun getCurrentWeather(locationDetails: LocationDetails?): WeatherDTO?

    suspend fun getHourlyForecast(locationDetails: LocationDetails?): HourlyForecastDTO?

    suspend fun getDailyForecast(locationDetails: LocationDetails?): DailyForecastDTO?

    suspend fun getClimateForecast(locationDetails: LocationDetails?): DailyForecastDTO?
}