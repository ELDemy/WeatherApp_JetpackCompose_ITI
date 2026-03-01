package com.dmy.weather.data.mapper

import com.dmy.weather.data.dto.HourlyForecastDTO
import com.dmy.weather.data.dto.HourlyForecastItem
import com.dmy.weather.data.enums.AlertType.CLOUDS
import com.dmy.weather.data.enums.AlertType.HUMIDITY
import com.dmy.weather.data.enums.AlertType.PRESSURE
import com.dmy.weather.data.enums.AlertType.RAIN
import com.dmy.weather.data.enums.AlertType.SNOW
import com.dmy.weather.data.enums.AlertType.TEMP
import com.dmy.weather.data.model.AlertModel

fun HourlyForecastDTO.filterBasedOnAlerts(alert: AlertModel): Pair<HourlyForecastItem, AlertModel>? {
    this.list?.forEach { item ->
        val matchedAlert = when (alert.alertType) {
            TEMP ->
                item.main?.temp?.let { it >= alert.max || it <= alert.min }
                    ?: false

            HUMIDITY ->
                item.main?.humidity?.let { it >= alert.max || it <= alert.min }
                    ?: false

            PRESSURE ->
                item.main?.pressure?.let { it >= alert.max || it <= alert.min }
                    ?: false

            RAIN, SNOW, CLOUDS ->
                item.weather?.firstOrNull()?.description == alert.alertType.desc

            null -> false
        }

        if (matchedAlert) return Pair(item, alert)
    }

    return null
}