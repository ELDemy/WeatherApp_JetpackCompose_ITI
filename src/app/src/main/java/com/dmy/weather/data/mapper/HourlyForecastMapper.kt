package com.dmy.weather.data.mapper

import com.dmy.weather.data.dto.HourlyForecastDTO
import com.dmy.weather.data.model.HourlyForecastModel
import com.dmy.weather.data.model.HourlyWeatherModel

fun HourlyForecastDTO.toModel(): HourlyForecastModel {
    val items = list?.map { item ->
        val condition = item.weather?.firstOrNull()

        HourlyWeatherModel(
            time = item.dt.getTimeInHours(),
            temperature = item.main?.temp,
            icon = iconMapper(condition?.icon),
            bg = backgroundMapper(item.weather?.firstOrNull()?.icon),
            description = condition?.description ?: "",
            clouds = item.clouds?.all
        )
    } ?: emptyList()

    return HourlyForecastModel(
        cityName = city?.name ?: "Unknown",
        hourlyItems = items
    )
}