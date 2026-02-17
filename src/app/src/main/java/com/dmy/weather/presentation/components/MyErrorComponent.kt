package com.dmy.weather.presentation.components

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier


@Composable
fun MyErrorComponent(error: String, modifier: Modifier = Modifier) {
    Text(error)
}