package com.dmy.weather.presentation.alerts_screen.components

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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dmy.weather.R
import com.dmy.weather.data.enums.AlertType
import com.dmy.weather.data.model.AlertEntity
import com.dmy.weather.platform.notification.NotificationType

@Composable
fun ConditionAlertItem(
    type: AlertType,
    alert: AlertEntity?,
    onToggle: (Boolean) -> Unit,
    onMinutesChange: (Int) -> Unit,
    onNotificationTypeChange: (NotificationType) -> Unit
) {
    val isEnabled = alert?.status == true
    val (icon, label, subtitle) = getAlertMeta(type)

    val borderColor =
        if (isEnabled) colorResource(R.color.purple_border)
        else colorResource(R.color.purple_border)

    val innerBorderColor =
        if (isEnabled) colorResource(R.color.purple_border)
        else colorResource(R.color.white)

    val bgColor =
        if (isEnabled) colorResource(R.color.purple_background)
        else colorResource(R.color.white)


    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(bgColor)
            .border(2.dp, borderColor, RoundedCornerShape(12.dp))
            .padding(16.dp),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
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
                    Text(subtitle, fontSize = 12.sp, color = colorResource(R.color.text_grey))
                }
            }
            Switch(
                checked = isEnabled,
                onCheckedChange = onToggle,
                colors = SwitchDefaults.colors(
                    checkedThumbColor = colorResource(R.color.white),
                    checkedTrackColor = colorResource(R.color.purple),
                    uncheckedThumbColor = colorResource(R.color.white),
                    uncheckedTrackColor = colorResource(R.color.grey_grad2),
                )
            )
        }

        AnimatedVisibility(
            visible = isEnabled,
            enter = expandVertically(),
            exit = shrinkVertically()
        ) {
            AlertExtraConfig(
                alert = alert,
                onMinutesChange = onMinutesChange,
                onNotificationTypeChange = onNotificationTypeChange
            )
        }
    }
}
