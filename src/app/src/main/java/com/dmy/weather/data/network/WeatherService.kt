package com.dmy.weather.data.network

import com.dmy.weather.data.dto.DailyForecastDTO
import com.dmy.weather.data.dto.HourlyForecastDTO
import com.dmy.weather.data.dto.WeatherDTO
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherService {

    @GET("data/2.5/weather")
    suspend fun getCurrentWeather(
        @Query("q") city: String,
    ): WeatherDTO?

    @GET("data/2.5/weather")
    suspend fun getCurrentWeather(
        @Query("lon") lon: String,
        @Query("lat") lat: String,
    ): WeatherDTO?

    @GET("data/2.5/forecast/hourly")
    suspend fun getHourlyForecast(
        @Query("q") city: String,
        @Query("cnt") count: Int = 24,
    ): HourlyForecastDTO?

    @GET("data/2.5/forecast/hourly")
    suspend fun getHourlyForecast(
        @Query("lon") lon: String,
        @Query("lat") lat: String,
        @Query("cnt") count: Int = 24,
    ): HourlyForecastDTO?

    @GET("data/2.5/forecast/daily")
    suspend fun getDailyForecast(
        @Query("q") city: String,
        @Query("cnt") count: Int = 7,
    ): DailyForecastDTO?

    @GET("data/2.5/forecast/daily")
    suspend fun getDailyForecast(
        @Query("lon") lon: String,
        @Query("lat") lat: String,
        @Query("cnt") count: Int = 7,
    ): DailyForecastDTO?


    @GET("data/2.5/forecast/climate")
    suspend fun getClimateForecast(
        @Query("q") city: String,
        @Query("cnt") count: Int = 12,
    ): DailyForecastDTO?

    @GET("data/2.5/forecast/climate")
    suspend fun getClimateForecast(
        @Query("lon") lon: String,
        @Query("lat") lat: String,
        @Query("cnt") count: Int = 12,
    ): DailyForecastDTO?


}

