package com.dmy.weather.presentation.settings_screen.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dmy.weather.data.enums.UnitSystem
import com.dmy.weather.presentation.settings_screen.SettingsVM


@Composable
fun UnitSystemSettings(viewModel: SettingsVM, unitSystem: UnitSystem) {
    SettingsCard(
        headerColor = Color(0xFFF5F3FF),
        headerBorderColor = Color(0xFFDDD6FE),
        header = {
            Text("📊", fontSize = 18.sp)
            Spacer(Modifier.width(8.dp))
            Text(
                "Unit System",
                fontWeight = FontWeight.SemiBold,
                color = Color(0xFF6D28D9)
            )
        }
    ) {
        UnitOption(
            label = "Metric",
            subtitle = "Celsius (°C) • Meter/sec (m/s)",
            selected = unitSystem == UnitSystem.METRIC,
            onClick = { viewModel.updateUnitSystem(UnitSystem.METRIC) }
        )
        Spacer(Modifier.height(8.dp))
        UnitOption(
            label = "Imperial",
            subtitle = "Fahrenheit (°F) • Miles/hour (mph)",
            selected = unitSystem == UnitSystem.IMPERIAL,
            onClick = { viewModel.updateUnitSystem(UnitSystem.IMPERIAL) }
        )
        Spacer(Modifier.height(8.dp))
        UnitOption(
            label = "Standard",
            subtitle = "Kelvin (K) • Meter/sec (m/s)",
            selected = unitSystem == UnitSystem.STANDARD,
            onClick = { viewModel.updateUnitSystem(UnitSystem.STANDARD) }
        )
    }
}

@Composable
private fun UnitOption(
    label: String,
    subtitle: String,
    selected: Boolean,
    onClick: () -> Unit
) {
    val bgBrush = if (selected)
        Brush.linearGradient(listOf(Color(0xFF7C3AED), Color(0xFF4F46E5)))
    else
        Brush.linearGradient(listOf(Color(0xFFF3F4F6), Color(0xFFF3F4F6)))

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(bgBrush)
            .clickable(onClick = onClick)
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column {
            Text(
                label,
                fontWeight = FontWeight.SemiBold,
                color = if (selected) Color.White else Color(0xFF111827)
            )
            Text(
                subtitle,
                fontSize = 13.sp,
                color = if (selected) Color.White.copy(alpha = 0.85f) else Color(0xFF6B7280)
            )
        }
        Box(
            modifier = Modifier
                .size(24.dp)
                .clip(CircleShape)
                .background(if (selected) Color.White.copy(alpha = 0.3f) else Color.Transparent)
                .then(
                    if (!selected) Modifier.border(2.dp, Color(0xFFD1D5DB), CircleShape)
                    else Modifier
                ),
            contentAlignment = Alignment.Center
        ) {
            if (selected) {
                Box(
                    modifier = Modifier
                        .size(12.dp)
                        .clip(CircleShape)
                        .background(Color.White)
                )
            }
        }
    }
}