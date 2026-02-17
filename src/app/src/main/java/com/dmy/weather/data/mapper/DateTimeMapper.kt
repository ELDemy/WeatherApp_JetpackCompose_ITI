package com.dmy.weather.data.mapper

import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Locale


fun getTimeInHours(time: Long): String {
    val pattern = "EEEE, MMM d"

    val instant = Instant.ofEpochSecond(time)
    val formatter = DateTimeFormatter.ofPattern(pattern, Locale.getDefault())

    return instant.atZone(ZoneId.systemDefault()).format(formatter)
}

fun getTimeInHours(time: String): String {
    return time.substringAfter(" ").substringBeforeLast(":")
}