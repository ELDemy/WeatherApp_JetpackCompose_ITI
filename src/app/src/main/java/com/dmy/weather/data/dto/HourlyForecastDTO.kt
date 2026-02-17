package com.dmy.weather.data.dto

import com.google.gson.annotations.SerializedName

data class HourlyForecastDTO(
    val city: CityDTO?,
    val list: List<HourlyForecastItem>?,
)

data class HourlyForecastItem(
    val dt: Long,
    val main: Main?,
    val weather: List<Weather>?,
    val clouds: Clouds?,
    val wind: Wind?,
    val visibility: Int?,
    val pop: Double?,
    @SerializedName("dt_txt") val dtTxt: String?
)