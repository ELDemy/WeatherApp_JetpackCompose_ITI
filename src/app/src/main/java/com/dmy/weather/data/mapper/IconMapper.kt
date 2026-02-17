package com.dmy.weather.data.mapper

fun iconMapper(icon: String?): String? {
    return if (icon != null) "https://openweathermap.org/img/wn/${icon}@2x.png"
    else null
}