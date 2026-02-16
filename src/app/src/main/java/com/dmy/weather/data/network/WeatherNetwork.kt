package com.dmy.weather.data.network

import com.dmy.weather.BuildConfig
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object WeatherNetwork {
    private const val BASE_URL = "https://api.openweathermap.org/data/2.5/"

    private val client = OkHttpClient.Builder()
        .addInterceptor { chain ->
            val original = chain.request()
            val url = original.url.newBuilder()
                .addQueryParameter("appid", BuildConfig.WEATHER_API_KEY)
                .build()

            chain.proceed(original.newBuilder().url(url).build())
        }
        .build()

    val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val weatherService: WeatherService = retrofit.create(WeatherService::class.java)
}