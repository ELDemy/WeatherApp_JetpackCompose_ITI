package com.dmy.weather.presentation.home_screen.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.dmy.weather.R

@Composable
fun WeatherIcon(iconRes: Int?, modifier: Modifier = Modifier.size(72.dp)) {
    iconRes?.let { res ->
        Image(
            painter = painterResource(id = res),
            contentDescription = stringResource(R.string.Weather_condition_icon),
            modifier = modifier
        )
    }
}