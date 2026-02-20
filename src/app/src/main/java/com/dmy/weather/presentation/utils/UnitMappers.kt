package com.dmy.weather.presentation.utils

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.dmy.weather.R
import com.dmy.weather.data.enums.UnitSystem
import com.dmy.weather.data.enums.UnitSystem.IMPERIAL
import com.dmy.weather.data.enums.UnitSystem.METRIC
import com.dmy.weather.data.enums.UnitSystem.STANDARD

@Composable
fun Double?.toTemp(unit: UnitSystem? = null): String {
    return when (unit) {
        STANDARD -> "${this?.toInt() ?: ""}" + "°${stringResource(R.string.K)}"
        METRIC -> "${this?.toInt() ?: ""}" + "°${stringResource(R.string.C)}"
        IMPERIAL -> "${this?.toInt() ?: ""}" + "°${stringResource(R.string.F)}"
        null -> "${this?.toInt() ?: ""}°"
    }
}

fun Int?.toHumidity(): String {
    return "${this ?: ""} %"
}

fun Double?.toRain(): String {
    return "${this ?: ""} %"
}

@Composable
fun Int?.toPressure(): String {
    return "${this ?: ""} ${stringResource(R.string.hPa)}"
}

@Composable
fun Int?.toClouds(): String {
    return "${this ?: ""} %"
}

@Composable
fun Double?.toSpeed(unit: UnitSystem?): String {
    return when (unit) {
        IMPERIAL -> "${this ?: ""} ${stringResource(R.string.mil_h)}"
        METRIC, STANDARD, null -> "${this ?: ""} ${stringResource(R.string.m_s)}"
    }
}