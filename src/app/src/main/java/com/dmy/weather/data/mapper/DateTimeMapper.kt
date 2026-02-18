package com.dmy.weather.data.mapper

import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Locale


fun Long.getTimeInHours(): String {
    return getTime(this, "h a")
}

fun Long.getTimeInFullDate(): String {
    return getTime(this, "EEEE, MMMM d, YYYY • h:mm a")
}

fun Long.getTimeInDayOfTheWeek(): String {
    return getTime(this, "EEEE")
}

private fun getTime(time: Long, pattern: String): String {
    val instant = Instant.ofEpochSecond(time)
    val formatter = DateTimeFormatter.ofPattern(pattern, Locale.getDefault())

    return instant.atZone(ZoneId.systemDefault()).format(formatter)
}