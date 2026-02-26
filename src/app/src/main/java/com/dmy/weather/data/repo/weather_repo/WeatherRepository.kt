package com.dmy.weather.data.repo.weather_repo

import com.dmy.weather.data.model.AlertEntity
import com.dmy.weather.data.model.DailyForecastModel
import com.dmy.weather.data.model.HourlyForecastModel
import com.dmy.weather.data.model.LocationDetails
import com.dmy.weather.data.model.NotificationWeatherModel
import com.dmy.weather.data.model.WeatherModel

interface WeatherRepository {
    suspend fun getCurrentWeather(): Result<WeatherModel>

    suspend fun getWeather(locationDetails: LocationDetails): Result<WeatherModel>

    suspend fun getHourlyForecast(locationDetails: LocationDetails): Result<HourlyForecastModel>

    suspend fun getDailyForecast(locationDetails: LocationDetails): Result<DailyForecastModel>

    suspend fun getAlertWeather(): Result<Pair<NotificationWeatherModel, AlertEntity>>

}