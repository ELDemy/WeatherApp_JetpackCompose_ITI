package com.dmy.weather.data.mapper

import com.dmy.weather.data.dto.WeatherDTO
import com.dmy.weather.data.model.WeatherModel

fun WeatherDTO.toModel(): WeatherModel {
    val condition = weather?.firstOrNull()

    return WeatherModel(
        cityName = name ?: "Unknown Location",
        temperature = main?.temp,
        description = condition?.description?.replaceFirstChar { it.uppercase() }
            ?: "No Description",
        iconUrl = iconMapper(condition?.icon),
        humidity = "${main?.humidity ?: 0}%",
        pressure = "${main?.pressure ?: 0} hPa"
    )
}