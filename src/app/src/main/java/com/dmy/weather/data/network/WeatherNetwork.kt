package com.dmy.weather.data.network

import com.dmy.weather.BuildConfig
import com.dmy.weather.data.repo.SettingsRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class WeatherNetwork(private val settingsRepository: SettingsRepository) {
    private val client = OkHttpClient.Builder()
        .addInterceptor { chain ->

            val settings = runBlocking { settingsRepository.settingsFlow.first() }
            val url = chain.request().url.newBuilder()
                .addQueryParameter("appid", BuildConfig.WEATHER_API_KEY)
                .addQueryParameter("lang", settings.lang?.apiCode)
                .addQueryParameter("units", settings.unit?.display)
                .build()
            chain.proceed(chain.request().newBuilder().url(url).build())
        }
        .build()

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://api.openweathermap.org/")
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val weatherService: WeatherService = retrofit.create(WeatherService::class.java)
    val geocodingService: GeocodingService = retrofit.create(GeocodingService::class.java)
}