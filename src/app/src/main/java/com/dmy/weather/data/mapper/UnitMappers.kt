package com.dmy.weather.data.mapper

import com.dmy.weather.data.enums.UnitSystem
import com.dmy.weather.data.enums.UnitSystem.IMPERIAL
import com.dmy.weather.data.enums.UnitSystem.METRIC
import com.dmy.weather.data.enums.UnitSystem.STANDARD

fun Double?.toTemp(unit: UnitSystem? = null): String {
    return when (unit) {
        STANDARD -> "${this?.toInt() ?: ""}°K"
        METRIC -> "${this?.toInt() ?: ""}°C"
        IMPERIAL -> "${this?.toInt() ?: ""}°F"
        null -> "${this?.toInt() ?: ""}°"
    }
}

fun Int?.toHumidity(): String {
    return "${this ?: ""} %"
}

fun Double?.toRain(): String {
    return "${this ?: ""} %"
}

fun Int?.toPressure(): String {
    return "${this ?: ""} hPa"
}

fun Int?.toClouds(): String {
    return "${this ?: ""} %"
}

fun Double?.toSpeed(unit: UnitSystem?): String {
    return when (unit) {
        IMPERIAL -> "${this ?: ""} mil/h"
        METRIC, STANDARD, null -> "${this ?: ""} m/s"
    }
}