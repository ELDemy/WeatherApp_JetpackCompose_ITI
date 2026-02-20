package com.dmy.weather.presentation.language_selection_screen.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dmy.weather.R
import com.dmy.weather.data.enums.AppLanguage

@Composable
fun LanguageInfo(
    language: AppLanguage,
    modifier: Modifier = Modifier,
    isSelected: Boolean,
    showRtlBadge: Boolean = false
) {
    val languageColor = colorResource(if (isSelected) R.color.text_white else R.color.text_primary)
    val codeColor =
        colorResource(if (isSelected) R.color.text_white else R.color.text_grey).copy(alpha = 0.8f)

    Column(modifier) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = language.displayName,
                fontWeight = FontWeight.Medium,
                color = languageColor,
                fontSize = 15.sp
            )
            if (showRtlBadge && language.isRtl) {
                Spacer(Modifier.width(6.dp))
                RtlBadge()
            }
        }
        Text(
            text = language.apiCode,
            color = codeColor,
            fontSize = 12.sp,
            fontFamily = FontFamily.Monospace
        )
    }
}
