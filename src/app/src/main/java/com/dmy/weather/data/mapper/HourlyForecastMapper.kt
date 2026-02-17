package com.dmy.weather.data.mapper


import com.dmy.weather.data.dto.HourlyForecastDTO
import com.dmy.weather.data.model.HourlyForecastModel
import com.dmy.weather.data.model.HourlyWeatherModel

fun HourlyForecastDTO.toModel(): HourlyForecastModel {
    val items = list?.map { item ->
        val condition = item.weather?.firstOrNull()

        val hour = item.dtTxt?.substringAfter(" ")?.substringBeforeLast(":") ?: "00:00"

        HourlyWeatherModel(
            time = hour,
            temperature = item.main?.temp,
            iconUrl = iconMapper(condition?.icon),
            description = condition?.description ?: ""
        )
    } ?: emptyList()

    return HourlyForecastModel(
        cityName = city?.name ?: "Unknown",
        hourlyItems = items
    )
}