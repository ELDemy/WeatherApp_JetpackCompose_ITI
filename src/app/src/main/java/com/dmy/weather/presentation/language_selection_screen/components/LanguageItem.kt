package com.dmy.weather.presentation.language_selection_screen.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dmy.weather.R
import com.dmy.weather.data.enums.AppLanguage


@Composable
fun LanguageItem(
    language: AppLanguage,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val gradientStart = colorResource(R.color.blue_primary)
    val gradientEnd = colorResource(R.color.blue_grad1)

    Row(
        modifier = modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .background(
                if (isSelected) Brush.horizontalGradient(
                    listOf(
                        gradientStart,
                        gradientEnd
                    )
                )
                else Brush.horizontalGradient(
                    listOf(Color.Transparent, Color.Transparent)
                )
            )
            .padding(horizontal = 16.dp, vertical = 14.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(language.flagEmoji, fontSize = 22.sp)
        Spacer(Modifier.width(12.dp))
        LanguageInfo(
            language = language,
            showRtlBadge = true,
            modifier = Modifier.weight(1f),
            isSelected = isSelected
        )
        RightCheckCircle(filled = isSelected)
    }
}