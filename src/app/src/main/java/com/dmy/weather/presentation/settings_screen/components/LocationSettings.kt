package com.dmy.weather.presentation.settings_screen.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Icon
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dmy.weather.data.enums.LocationMode
import com.dmy.weather.presentation.settings_screen.SettingsVM


@Composable
fun LocationSettings(viewModel: SettingsVM, locationMode: LocationMode) {
    SettingsCard(
        headerColor = Color(0xFFEFF6FF),
        headerBorderColor = Color(0xFFBFDBFE),
        header = {
            Icon(
                Icons.Filled.LocationOn,
                contentDescription = null,
                tint = Color(0xFF1D4ED8),
                modifier = Modifier.size(20.dp)
            )
            Spacer(Modifier.width(8.dp))
            Text(
                "Location Settings",
                fontWeight = FontWeight.SemiBold,
                color = Color(0xFF1D4ED8)
            )
        }
    ) {
        LocationOption(
            label = "GPS Location",
            subtitle = "Use device GPS",
            selected = locationMode == LocationMode.GPS,
            onClick = { viewModel.updateLocationMode(LocationMode.GPS) }
        )
        Spacer(Modifier.height(8.dp))
        LocationOption(
            label = "Map Selection",
            subtitle = "Pick location on map",
            selected = locationMode == LocationMode.MAP,
            onClick = { viewModel.updateLocationMode(LocationMode.MAP) }
        )
    }
}

@Composable
private fun LocationOption(
    label: String,
    subtitle: String,
    selected: Boolean,
    onClick: () -> Unit
) {
    val borderColor = if (selected) Color(0xFF2563EB) else Color(0xFFE5E7EB)
    val bgColor = if (selected) Color(0xFFEFF6FF) else Color.White

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(bgColor)
            .then(
                Modifier.border(2.dp, borderColor, RoundedCornerShape(12.dp))
            )
            .clickable(onClick = onClick)
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        RadioButton(
            selected = selected,
            onClick = onClick,
            colors = RadioButtonDefaults.colors(
                selectedColor = Color(0xFF2563EB),
                unselectedColor = Color(0xFFD1D5DB)
            ),
            modifier = Modifier.size(20.dp)
        )
        Spacer(Modifier.width(12.dp))
        Column {
            Text(label, fontWeight = FontWeight.Medium, color = Color(0xFF111827))
            Text(subtitle, fontSize = 13.sp, color = Color(0xFF6B7280))
        }
    }
}