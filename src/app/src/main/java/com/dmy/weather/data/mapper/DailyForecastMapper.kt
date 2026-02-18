package com.dmy.weather.data.mapper


import com.dmy.weather.data.dto.DailyForecastDTO
import com.dmy.weather.data.model.DailyForecastModel
import com.dmy.weather.data.model.DailyWeatherModel

fun DailyForecastDTO.toModel(): DailyForecastModel {
    return DailyForecastModel(
        cityName = city?.name ?: "Unknown Location",
        forecasts = list?.map { item ->
            DailyWeatherModel(
                dateTime = item.dt.getTimeInDayOfTheWeek(),
                tempDay = item.temp?.day?.toTemp() ?: "",
                tempMin = item.temp?.min?.toTemp() ?: "",
                tempMax = item.temp?.max?.toTemp() ?: "",
                condition = item.weather?.firstOrNull()?.main ?: "Unknown",
                description = item.weather?.firstOrNull()?.description ?: "",
                icon = iconMapper(item.weather?.firstOrNull()?.icon),
                humidity = item.humidity.toHumidity(),
                windSpeed = item.speed.toSpeed(),
                rainChance = item.pop.toRain()
            )
        } ?: emptyList()
    )
}