package com.dmy.weather.presentation.language_selection_screen.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dmy.weather.R
import com.dmy.weather.data.enums.AppLanguage


@Composable
fun SelectedLanguageCard(language: AppLanguage, modifier: Modifier = Modifier) {
    val gradientStart = colorResource(R.color.blue_primary)
    val gradientEnd = colorResource(R.color.blue_grad1)

    Surface(
        shape = RoundedCornerShape(16.dp),
        modifier = modifier.fillMaxWidth()
    ) {
        Box(
            Modifier.background(
                Brush.horizontalGradient(listOf(gradientStart, gradientEnd))
            )
        ) {
            Row(
                modifier = Modifier.padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(language.flagEmoji, fontSize = 28.sp)
                Spacer(Modifier.width(12.dp))
                LanguageInfo(
                    language = language,
                    modifier = Modifier.weight(1f),
                    isSelected = true
                )
                RightCheckCircle(filled = true, size = 32.dp)

            }
        }
    }
}
