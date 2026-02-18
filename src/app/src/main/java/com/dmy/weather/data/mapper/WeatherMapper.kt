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
        temperature = main?.temp.toTemp(),
        feelsLike = main?.feelsLike.toTemp(),
        min = main?.tempMin.toTemp(),
        max = main?.tempMax.toTemp(),
        description = condition?.description?.replaceFirstChar { it.uppercase() } ?: "UnKnown",
        iconUrl = iconMapper(condition?.icon),
        humidity = main?.humidity.toHumidity(),
        pressure = main?.pressure.toPressure(),
        windSpeed = wind?.speed.toSpeed(),
        clouds = clouds?.all.toClouds(),
    )
}