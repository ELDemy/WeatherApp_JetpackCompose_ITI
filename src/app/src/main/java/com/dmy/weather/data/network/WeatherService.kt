package com.dmy.weather.data.network

import com.dmy.weather.data.dto.WeatherDTO
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherService {

    @GET("weather")
    suspend fun getCurrentWeather(
        @Query("q") city: String,
        @Query("units") units: String = "metric"
    ): WeatherDTO?
}

