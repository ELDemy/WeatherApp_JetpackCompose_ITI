package com.dmy.weather.data.mapper

import com.dmy.weather.data.dto.CityDTO
import com.dmy.weather.data.dto.HourlyForecastDTO
import com.dmy.weather.data.dto.HourlyForecastItem
import com.dmy.weather.data.model.HourlyForecastModel
import com.dmy.weather.data.model.HourlyWeatherModel
import com.dmy.weather.data.model.NotificationWeatherModel

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

fun HourlyForecastItem.toNotificationModel(city: CityDTO?): NotificationWeatherModel {
    val condition = weather?.firstOrNull()

    return NotificationWeatherModel(
        dt = dt,
        cityName = city?.name ?: "city",
        country = city?.country ?: "Country",
        time = dt.getTimeInHours(),
        temperature = main?.temp,
        min = null,
        max = null,
        icon = iconMapper(condition?.icon),
        bg = backgroundMapper(weather?.firstOrNull()?.icon),
        description = condition?.description ?: "",
    )
}