package com.dmy.weather.data.mapper

import com.dmy.weather.data.dto.WeatherDTO
import com.dmy.weather.data.model.WeatherModel
import java.util.Date

fun WeatherDTO.toModel(): WeatherModel {
    val condition = weather?.firstOrNull()

    return WeatherModel(
        cityName = name ?: "Unknown Location",
        country = sys?.country ?: "Unknown Country",
        time = dt?.getTimeInFullDate() ?: Date().time.getTimeInFullDate(),
        temperature = main?.temp,
        feelsLike = main?.feelsLike,
        min = main?.tempMin,
        max = main?.tempMax,
        description = condition?.description?.replaceFirstChar { it.uppercase() } ?: "UnKnown",
        iconUrl = iconMapper(condition?.icon),
        humidity = main?.humidity,
        pressure = main?.pressure,
        windSpeed = wind?.speed,
        clouds = clouds?.all,
    )
}