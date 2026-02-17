package com.dmy.weather.presentation.components

import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import com.dmy.weather.R

@Composable
fun MyLoadingComponent(color: Int = R.color.blue_primary, modifier: Modifier = Modifier) {
    CircularProgressIndicator(modifier = modifier, color = colorResource(color))
}