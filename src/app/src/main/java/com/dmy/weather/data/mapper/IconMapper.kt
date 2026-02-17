package com.dmy.weather.data.mapper

fun iconMapper(icon: String?): String? {
    //other link https://openweathermap.org/payload/api/media/file/10d.png
    return if (icon != null) "https://openweathermap.org/img/wn/${icon}@2x.png"
    else null
}