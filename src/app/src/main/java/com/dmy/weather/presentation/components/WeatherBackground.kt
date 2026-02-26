package com.dmy.weather.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import com.dmy.weather.R.color

@Composable
fun WeatherBackground(bg: Int?, modifier: Modifier) {
    if (bg != null) {
        Image(
            painter = painterResource(id = bg),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = modifier
        )

    } else {
        Box(
            modifier = modifier
                .background(
                    Brush.linearGradient(
                        colors = listOf(
                            colorResource(color.blue_grad1),
                            colorResource(color.blue_primary),
                            colorResource(color.blue_grad3),
                        )
                    )
                )
        )
    }
}