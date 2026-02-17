package com.dmy.weather.data.mapper


import com.dmy.weather.data.dto.DailyForecastDTO
import com.dmy.weather.data.model.DailyForecastModel
import com.dmy.weather.data.model.DailyWeatherModel
import kotlin.math.roundToInt

fun DailyForecastDTO.toModel(): DailyForecastModel {
    return DailyForecastModel(
        cityName = city?.name ?: "Unknown Location",
        forecasts = list?.map { item ->
            DailyWeatherModel(
                dateTime = getTimeInHours(item.dt),
                tempDay = item.temp?.day?.roundToInt() ?: 0,
                tempMin = item.temp?.min?.roundToInt() ?: 0,
                tempMax = item.temp?.max?.roundToInt() ?: 0,
                condition = item.weather?.firstOrNull()?.main ?: "Unknown",
                description = item.weather?.firstOrNull()?.description ?: "",
                icon = item.weather?.firstOrNull()?.icon ?: "",
                humidity = item.humidity ?: 0,
                windSpeed = item.speed ?: 0.0,
                rainChance = ((item.pop ?: 0.0) * 100).roundToInt()
            )
        } ?: emptyList()
    )
}