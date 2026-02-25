package com.dmy.weather.presentation.alerts_screen.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.dmy.weather.R
import com.dmy.weather.data.enums.AlertType
import com.dmy.weather.data.enums.UnitSystem
import com.dmy.weather.presentation.alerts_screen.AlertMeta

@Composable
fun getAlertMeta(type: AlertType, unitSystem: UnitSystem? = null): AlertMeta {
    val temp = stringResource(R.string.Temperature)
    return when (type) {
        AlertType.TEMP -> when (unitSystem) {
            UnitSystem.METRIC -> AlertMeta(
                "🌡️",
                temp,
                stringResource(R.string.C),
                -50f,
                60f
            )

            UnitSystem.IMPERIAL -> AlertMeta(
                "🌡️",
                temp,
                stringResource(R.string.F),
                -58f,
                140f
            )

            UnitSystem.STANDARD -> AlertMeta(
                "🌡️",
                temp,
                stringResource(R.string.K),
                223f,
                333f
            )

            null -> AlertMeta("", "", "", 0f, 0f)
        }

        AlertType.HUMIDITY -> AlertMeta("💧", stringResource(R.string.Humidity), "%", 0f, 100f)
        AlertType.PRESSURE -> AlertMeta(
            "🔵",
            stringResource(R.string.Pressure),
            stringResource(R.string.hPa),
            900f,
            1100f
        )

        AlertType.RAIN -> AlertMeta(
            "🌧️",
            stringResource(R.string.Rain),
            stringResource(R.string.Notify_when_rain_is_detected),
            0f,
            0f
        )

        AlertType.SNOW -> AlertMeta(
            "❄️",
            stringResource(R.string.Snow),
            stringResource(R.string.Notify_when_snow_is_detected),
            0f,
            0f
        )

        AlertType.CLOUDS -> AlertMeta(
            "☁️",
            stringResource(R.string.Clouds),
            stringResource(R.string.Notify_on_heavy_cloud_cover),
            0f,
            0f
        )

    }
}