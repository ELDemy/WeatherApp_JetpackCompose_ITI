@file:OptIn(ExperimentalMaterial3Api::class)

package com.dmy.weather.presentation.app_bar

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import com.dmy.weather.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShrinkAppBar() {
    TopAppBar(
        title = { },
        expandedHeight = 0.dp,
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = colorResource(R.color.blue_primary)
        )
    )
}
