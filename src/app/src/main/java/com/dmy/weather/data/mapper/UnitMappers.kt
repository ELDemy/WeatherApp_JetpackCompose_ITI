package com.dmy.weather.data.mapper

fun Double?.toTemp(): String {
    return "${this?.toInt() ?: ""}°"
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

fun Double?.toSpeed(): String {
    return "${this ?: ""} m/s"
}