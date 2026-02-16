package com.dmy.weather.mapper

import com.dmy.weather.data.dto.WeatherDTO
import com.dmy.weather.data.model.WeatherModel

fun WeatherDTO.toModel(): WeatherModel {
    val tempInt = main?.temp?.toInt() ?: 0
    val condition = weather?.firstOrNull()

    return WeatherModel(
        cityName = name ?: "Unknown Location",
        temperature = "$tempInt°C",
        description = condition?.description?.replaceFirstChar { it.uppercase() }
            ?: "No Description",
        iconUrl = if (condition != null) "https://openweathermap.org/img/wn/${condition.icon}@2x.png" else "",
        humidity = "${main?.humidity ?: 0}%",
        pressure = "${main?.pressure ?: 0} hPa"
    )
}