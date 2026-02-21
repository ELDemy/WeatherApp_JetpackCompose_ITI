package com.dmy.weather.presentation.weather_details_screen.components

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.dmy.weather.R.color

@Composable
fun SectionTitleText(text: String, modifier: Modifier = Modifier) {
    Text(
        modifier = modifier,
        text = text,
        fontSize = 18.sp,
        fontWeight = FontWeight.Bold,
        color = colorResource(color.text_primary)
    )
}