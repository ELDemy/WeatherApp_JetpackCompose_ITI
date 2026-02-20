package com.dmy.weather.presentation.language_selection_screen.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.dmy.weather.data.enums.AppLanguage

@Composable
fun LanguageList(
    languages: List<AppLanguage>,
    selected: AppLanguage,
    onSelect: (AppLanguage) -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        shape = RoundedCornerShape(16.dp),
        color = Color.White,
        shadowElevation = 1.dp,
        modifier = modifier
    ) {
        Column {
            languages.forEachIndexed { index, lang ->
                LanguageItem(
                    language = lang,
                    isSelected = lang == selected,
                    onClick = { onSelect(lang) }
                )
                if (index < languages.lastIndex) {
                    HorizontalDivider(color = Color(0xFFF3F4F6), thickness = 1.dp)
                }
            }
        }
    }
}