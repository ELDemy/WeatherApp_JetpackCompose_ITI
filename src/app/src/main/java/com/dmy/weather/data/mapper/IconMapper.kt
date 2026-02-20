package com.dmy.weather.data.mapper

import com.dmy.weather.R

//fun iconMapper(icon: String?): String? {
//    //other link https://openweathermap.org/payload/api/media/file/10d.png
//    return when (icon) {
////        "01n",//sunny
////        "02n",//partial cloud
//        null -> null
//
//        else -> "https://openweathermap.org/img/wn/${icon}@2x.png"
//    }
//}
fun iconMapper(icon: String?): Int? {
    return when (icon) {
        "01d" -> R.drawable.icon_01d
        "01n" -> R.drawable.icon_01n
        "02d" -> R.drawable.icon_02d
        "02n" -> R.drawable.icon_02n
        "03d" -> R.drawable.icon_03d
        "03n" -> R.drawable.icon_03n
        "04d" -> R.drawable.icon_04d
        "04n" -> R.drawable.icon_04n
        "09d" -> R.drawable.icon_09d
        "09n" -> R.drawable.icon_09n
        "10d" -> R.drawable.icon_10d
        "10n" -> R.drawable.icon_10n
        "11d" -> R.drawable.icon_11d
        "11n" -> R.drawable.icon_11n
        "13d" -> R.drawable.icon_13d
        "13n" -> R.drawable.icon_13n
        "50d" -> R.drawable.icon_50d
        "50n" -> R.drawable.icon_50n
        else -> null
    }
}

fun backgroundMapper(icon: String?): Int? {
    return when (icon) {
        "01d" -> R.drawable.bg_01d
        "01n" -> R.drawable.bg_01n
        "02d" -> R.drawable.bg_02d
        "02n" -> R.drawable.bg_02n
        "03d" -> R.drawable.bg_03d
        "03n" -> R.drawable.bg_03n
        "04d" -> R.drawable.bg_04d
        "04n" -> R.drawable.bg_04n
        "09d" -> R.drawable.bg_09d
        "09n" -> R.drawable.bg_09n
        "10d" -> R.drawable.bg_10d
        "10n" -> R.drawable.bg_10n
        "11d" -> R.drawable.bg_11d
        "11n" -> R.drawable.bg_11n
        "13d" -> R.drawable.bg_13d
        "13n" -> R.drawable.bg_13n
        "50d" -> R.drawable.bg_50d
        "50n" -> R.drawable.bg_50n
        else -> null
    }
}