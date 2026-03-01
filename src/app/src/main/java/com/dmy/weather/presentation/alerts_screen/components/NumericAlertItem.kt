package com.dmy.weather.presentation.alerts_screen.components

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dmy.weather.R
import com.dmy.weather.data.enums.AlertType
import com.dmy.weather.data.enums.NotificationType
import com.dmy.weather.data.enums.UnitSystem
import com.dmy.weather.data.model.AlertModel


@Composable
fun NumericAlertItem(
    type: AlertType,
    alert: AlertModel?,
    unitSystem: UnitSystem,
    onToggle: (Boolean) -> Unit,
    onRangeChange: (Int, Int) -> Unit,
    onMinutesChange: (Int) -> Unit,
    onNotificationTypeChange: (NotificationType) -> Unit
) {
    val isEnabled = alert?.status == true
    val (icon, label, unit, minRange, maxRange) = getAlertMeta(type, unitSystem)

    val borderColor =
        if (isEnabled) colorResource(R.color.blue_primary)
        else colorResource(R.color.grey_border)

    val bgColor =
        if (isEnabled) colorResource(R.color.lightBlue_background)
        else colorResource(R.color.white)

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(bgColor)
            .border(2.dp, borderColor, RoundedCornerShape(12.dp))
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(icon, fontSize = 20.sp)
                Spacer(Modifier.width(10.dp))
                Column {
                    Text(
                        label,
                        fontWeight = FontWeight.SemiBold,
                        color = colorResource(R.color.text_primary)
                    )
                    Text(
                        if (isEnabled) "${stringResource(R.string.Min)}: ${alert.min}$unit  ${
                            stringResource(R.string.Max)
                        }: ${alert.max}$unit"
                        else stringResource(R.string.Tap_to_configure),
                        fontSize = 12.sp,
                        color = colorResource(R.color.text_grey)
                    )
                }
            }
            Switch(
                checked = isEnabled,
                onCheckedChange = onToggle,
                colors = SwitchDefaults.colors(
                    checkedThumbColor = Color.White,
                    checkedTrackColor = colorResource(R.color.blue_primary),
                    uncheckedThumbColor = Color.White,
                    uncheckedTrackColor = colorResource(R.color.grey_grad2)
                )
            )
        }

        AnimatedVisibility(
            visible = isEnabled,
            enter = expandVertically(),
            exit = shrinkVertically()
        ) {
            Column(modifier = Modifier.padding(top = 12.dp)) {
                var currentMin by remember(alert?.min) {
                    mutableStateOf(alert?.min?.toFloat() ?: minRange)
                }
                var currentMax by remember(alert?.max) {
                    mutableStateOf(alert?.max?.toFloat() ?: maxRange)
                }

                Text(
                    "${stringResource(R.string.Min_threshold)}: ${currentMin.toInt()}$unit",
                    fontSize = 13.sp,
                    color = colorResource(R.color.text_grey),
                    fontWeight = FontWeight.Medium
                )
                Slider(
                    value = currentMin,
                    onValueChange = { currentMin = it },
                    onValueChangeFinished = {
                        Log.i("Slider", "NumericAlertItem: currentMin Finished")
                        onRangeChange(
                            currentMin.toInt(),
                            currentMax.toInt()
                        )
                    },
                    valueRange = minRange..maxRange,
                    colors = SliderDefaults.colors(
                        thumbColor = colorResource(R.color.blue_primary),
                        activeTrackColor = colorResource(R.color.blue_primary),
                        inactiveTrackColor = colorResource(R.color.blue_inActive),
                    )
                )
                Spacer(Modifier.height(4.dp))
                Text(
                    "${stringResource(R.string.Max_threshold)}: ${currentMax.toInt()}$unit",
                    fontSize = 13.sp,
                    color = colorResource(R.color.text_grey),
                    fontWeight = FontWeight.Medium
                )
                Slider(
                    value = currentMax,
                    onValueChange = { currentMax = it },
                    onValueChangeFinished = {
                        onRangeChange(
                            currentMin.toInt(),
                            currentMax.toInt()
                        )
                    },
                    valueRange = minRange..maxRange,
                    colors = SliderDefaults.colors(
                        thumbColor = colorResource(R.color.blue_primary),
                        activeTrackColor = colorResource(R.color.blue_primary),
                        inactiveTrackColor = colorResource(R.color.blue_inActive),
                    )
                )

                AlertExtraConfig(
                    alert = alert,
                    onMinutesChange = onMinutesChange,
                    onNotificationTypeChange = onNotificationTypeChange
                )
            }
        }
    }
}
