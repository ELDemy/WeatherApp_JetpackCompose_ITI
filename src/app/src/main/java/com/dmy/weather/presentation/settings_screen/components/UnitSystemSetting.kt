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
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dmy.weather.R
import com.dmy.weather.data.enums.UnitSystem
import com.dmy.weather.presentation.settings_screen.SettingsVM


@Composable
fun UnitSystemSettings(viewModel: SettingsVM, unitSystem: UnitSystem) {
    SettingsCard(
        headerColor = colorResource(R.color.purple_background),
        headerBorderColor = colorResource(R.color.purple_border_light),
        header = {
            Text("📊", fontSize = 18.sp)
            Spacer(Modifier.width(8.dp))
            Text(
                stringResource(R.string.Unit_System),
                fontWeight = FontWeight.SemiBold,
                color = colorResource(R.color.purple_foreground)
            )
        }
    ) {
        UnitOption(
            label = stringResource(R.string.Metric),
            subtitle = stringResource(R.string.Celsius_),
            selected = unitSystem == UnitSystem.METRIC,
            onClick = { viewModel.updateUnitSystem(UnitSystem.METRIC) }
        )
        Spacer(Modifier.height(8.dp))
        UnitOption(
            label = stringResource(R.string.Imperial),
            subtitle = stringResource(R.string.Fahrenheit_),
            selected = unitSystem == UnitSystem.IMPERIAL,
            onClick = { viewModel.updateUnitSystem(UnitSystem.IMPERIAL) }
        )
        Spacer(Modifier.height(8.dp))
        UnitOption(
            label = stringResource(R.string.Standard),
            subtitle = stringResource(R.string.Kelvin_),
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
        Brush.linearGradient(
            listOf(
                colorResource(R.color.purple_dark),
                colorResource(R.color.purple)
            )
        )
    else
        Brush.linearGradient(
            listOf(
                colorResource(R.color.grey_background),
                colorResource(R.color.grey_background)
            )
        )

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