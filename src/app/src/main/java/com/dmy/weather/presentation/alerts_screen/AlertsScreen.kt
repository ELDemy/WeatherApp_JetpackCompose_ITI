package com.dmy.weather.presentation.alerts_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.dmy.weather.R
import com.dmy.weather.data.enums.AlertType
import com.dmy.weather.presentation.alerts_screen.components.ConditionAlertItem
import com.dmy.weather.presentation.alerts_screen.components.NumericAlertItem
import com.dmy.weather.presentation.settings_screen.components.SettingsCard
import org.koin.androidx.compose.koinViewModel

@Composable
fun AlertsScreen(modifier: Modifier = Modifier) {
    val viewModel: AlertsVM = koinViewModel()
    val alerts by viewModel.alerts.collectAsStateWithLifecycle()
    val unit by viewModel::unit

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(colorResource(R.color.white_background))
            .verticalScroll(rememberScrollState())
            .padding(vertical = 24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        SettingsCard(
            headerColor = colorResource(R.color.lightBlue_background),
            headerBorderColor = colorResource(R.color.lightBlue_border),
            header = {
                Text("🌡️", fontSize = 18.sp)
                Spacer(Modifier.width(8.dp))
                Text(
                    stringResource(R.string.Threshold_Alerts),
                    fontWeight = FontWeight.SemiBold,
                    color = colorResource(R.color.blue_primary)
                )
            }
        ) {
            AlertType.entries.forEach { type ->
                val alert = alerts.find { it.alertType == type }
                when (type) {
                    AlertType.TEMP, AlertType.HUMIDITY, AlertType.PRESSURE -> {
                        NumericAlertItem(
                            type = type,
                            alert = alert,
                            unit,
                            onToggle = { viewModel.toggleAlert(type, it) },
                            onRangeChange = { min, max -> viewModel.updateRange(type, min, max) },
                            onMinutesChange = { viewModel.updateMinutesBefore(type, it) },
                            onNotificationTypeChange = {
                                viewModel.updateNotificationType(type, it)
                            }

                        )
                        if (type != AlertType.PRESSURE) Spacer(Modifier.height(8.dp))
                    }

                    AlertType.CLOUDS, AlertType.SNOW, AlertType.RAIN -> {}
                }
            }
        }

        SettingsCard(
            headerColor = colorResource(R.color.purple_background),
            headerBorderColor = colorResource(R.color.purple_border_light),
            header = {
                Text("🌦️", fontSize = 18.sp)
                Spacer(Modifier.width(8.dp))
                Text(
                    "Condition Alerts",
                    fontWeight = FontWeight.SemiBold,
                    color = colorResource(R.color.purple_foreground)
                )
            }
        ) {
            AlertType.entries.forEach { type ->
                val alert = alerts.find { it.alertType == type }
                when (type) {
                    AlertType.TEMP, AlertType.HUMIDITY, AlertType.PRESSURE -> {}

                    AlertType.CLOUDS, AlertType.SNOW, AlertType.RAIN -> {
                        ConditionAlertItem(
                            type = type,
                            alert = alert,
                            onToggle = { viewModel.toggleAlert(type, it) },
                            onMinutesChange = { viewModel.updateMinutesBefore(type, it) },
                            onNotificationTypeChange = {
                                viewModel.updateNotificationType(
                                    type,
                                    it
                                )
                            }

                        )

                        if (type != AlertType.RAIN) Spacer(Modifier.height(8.dp))
                    }
                }
            }
        }
    }
}


