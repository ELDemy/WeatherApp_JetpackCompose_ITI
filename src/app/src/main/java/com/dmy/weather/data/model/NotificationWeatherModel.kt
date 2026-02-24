package com.dmy.weather.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class NotificationWeatherModel(
    val cityName: String,
    val country: String,
    val dt: Long,
    val time: String,
    val description: String,
    val temperature: Double?,
    val min: Double?,
    val max: Double?,
    val icon: Int?,
    val bg: Int?,
) : Parcelable