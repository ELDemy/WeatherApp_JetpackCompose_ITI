package com.dmy.weather.data.dto

import com.google.gson.annotations.SerializedName

data class DailyForecastDTO(
    val city: CityDTO?,
    val list: List<DailyForecastItem>?,
)

data class DailyForecastItem(
    val dt: Long,
    val sunrise: Long?,
    val sunset: Long?,
    val temp: TempDTO?,
    @SerializedName("feels_like") val feelsLike: FeelsLikeDTO?,
    val pressure: Int?,
    val humidity: Int?,
    val weather: List<Weather>?,
    val speed: Double?,
    val deg: Int?,
    val gust: Double?,
    val clouds: Int?,
    val pop: Double?,
    val rain: Double?
)