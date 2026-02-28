package com.dmy.weather.presentation.alerts_screen.components


import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dmy.weather.R
import com.dmy.weather.data.model.AlertEntity
import com.dmy.weather.platform.notification.NotificationType

@Composable
fun AlertExtraConfig(
    alert: AlertEntity?,
    onMinutesChange: (Int) -> Unit,
    onNotificationTypeChange: (NotificationType) -> Unit
) {
    val currentMinutes = alert?.time ?: 30
    val currentType = alert?.notificationType ?: NotificationType.NOTIFY
    var minutesText by remember(currentMinutes) { mutableStateOf(currentMinutes.toString()) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {

        OutlinedTextField(
            value = minutesText,
            onValueChange = { input ->
                if (input.length <= 4 && input.all { it.isDigit() }) {
                    minutesText = input
                    input.toIntOrNull()?.let { onMinutesChange(it) }
                }
            },
            label = { Text(stringResource(R.string.Notify_me_before), fontSize = 12.sp) },
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(10.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = colorResource(R.color.blue_primary),
                unfocusedBorderColor = colorResource(R.color.grey_border),
                focusedLabelColor = colorResource(R.color.blue_primary),
                focusedTextColor = colorResource(R.color.text_primary),
                unfocusedTextColor = colorResource(R.color.text_grey),
                cursorColor = colorResource(R.color.blue_primary),
            )
        )

        Text(
            stringResource(R.string.Notification_type),
            fontSize = 13.sp,
            fontWeight = FontWeight.Medium,
            color = colorResource(R.color.text_grey)
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            NotificationType.entries.forEach { type ->
                val selected = currentType == type
                val (label, emoji) = when (type) {
                    NotificationType.UPDATES -> stringResource(R.string.Silent) to "🔕"
                    NotificationType.NOTIFY -> stringResource(R.string.Notify) to "🔔"
                    NotificationType.ALARM -> stringResource(R.string.Alarm) to "⏰"
                }
                NotificationTypeChip(
                    label = label,
                    emoji = emoji,
                    selected = selected,
                    modifier = Modifier.weight(1f),
                    onClick = { onNotificationTypeChange(type) }
                )
            }
        }
    }
}

@Composable
private fun NotificationTypeChip(
    label: String,
    emoji: String,
    selected: Boolean,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    val bgColor =
        if (selected) colorResource(R.color.blue_primary) else colorResource(R.color.white)
    val textColor = if (selected) Color.White else colorResource(R.color.text_grey)
    val borderColor =
        if (selected) colorResource(R.color.blue_primary) else colorResource(R.color.grey_border)

    Column(
        modifier = modifier
            .clip(RoundedCornerShape(10.dp))
            .background(bgColor)
            .border(1.5.dp, borderColor, RoundedCornerShape(10.dp))
            .clickable(onClick = onClick)
            .padding(vertical = 10.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(emoji, fontSize = 18.sp)
        Spacer(Modifier.height(2.dp))
        Text(label, fontSize = 11.sp, color = textColor, fontWeight = FontWeight.Medium)
    }
}