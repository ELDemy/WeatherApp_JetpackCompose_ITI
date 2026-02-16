package com.dmy.weather.data.dto

import com.google.gson.annotations.SerializedName

data class WeatherDTO(
    val coord: Coord?,
    val weather: List<Weather>?,
    val base: String?,
    val main: Main?,
    val visibility: Int?,
    val wind: Wind?,
    val clouds: Clouds?,
    val dt: Long?,
    val sys: Sys?,
    val timezone: Int?,
    val id: Int?,
    val name: String?,
    val cod: Int?
)

data class Coord(
    val lon: Double,
    val lat: Double
)

data class Weather(
    val id: Int,
    val main: String,       // e.g., "Clouds"
    val description: String, // e.g., "overcast clouds"
    val icon: String        // e.g., "04n" - used to fetch the image
)

data class Main(
    val temp: Double,
    @SerializedName("feels_like") val feelsLike: Double,
    @SerializedName("temp_min") val tempMin: Double,
    @SerializedName("temp_max") val tempMax: Double,
    val pressure: Int,
    val humidity: Int,
    @SerializedName("sea_level") val seaLevel: Int?,
    @SerializedName("grnd_level") val grndLevel: Int?
)

data class Wind(
    val speed: Double,
    val deg: Int,
    val gust: Double? // Use nullable for optional fields
)

data class Clouds(
    val all: Int // Cloudiness percentage
)

data class Sys(
    val country: String,
    val sunrise: Long,
    val sunset: Long
)