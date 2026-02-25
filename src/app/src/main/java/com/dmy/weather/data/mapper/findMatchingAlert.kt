package com.dmy.weather.data.mapper

import com.dmy.weather.data.dto.HourlyForecastDTO
import com.dmy.weather.data.dto.HourlyForecastItem
import com.dmy.weather.data.enums.AlertType.CLOUDS
import com.dmy.weather.data.enums.AlertType.HUMIDITY
import com.dmy.weather.data.enums.AlertType.PRESSURE
import com.dmy.weather.data.enums.AlertType.RAIN
import com.dmy.weather.data.enums.AlertType.SNOW
import com.dmy.weather.data.enums.AlertType.TEMP
import com.dmy.weather.data.model.AlertEntity

fun HourlyForecastDTO.filterBasedOnAlerts(activeAlerts: List<AlertEntity>): Pair<HourlyForecastItem, AlertEntity>? {
    if (activeAlerts.isEmpty()) return null
    
    this.list?.forEach { item ->
        val matchedAlert = activeAlerts.firstOrNull { alert ->
            when (alert.alertType) {
                TEMP ->
                    item.main?.temp?.let { return@firstOrNull it >= alert.max || it <= alert.min }
                        ?: false

                HUMIDITY ->
                    item.main?.humidity?.let { return@firstOrNull it >= alert.max || it <= alert.min }
                        ?: false

                PRESSURE ->
                    item.main?.pressure?.let { return@firstOrNull it >= alert.max || it <= alert.min }
                        ?: false

                RAIN, SNOW, CLOUDS ->
                    return@firstOrNull item.weather?.firstOrNull()?.description == alert.alertType.desc

                null -> false
            }
        }

        if (matchedAlert != null) return Pair(item, matchedAlert)
    }

    return null
}