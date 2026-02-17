package com.dmy.weather.data.mapper

fun iconMapper(icon: String?): String? {
    //other link https://openweathermap.org/payload/api/media/file/10d.png
    return when (icon) {
        "01n",//sunny
        "02n",//partial cloud
        null -> null

        else -> "https://openweathermap.org/img/wn/${icon}@2x.png"
    }
}