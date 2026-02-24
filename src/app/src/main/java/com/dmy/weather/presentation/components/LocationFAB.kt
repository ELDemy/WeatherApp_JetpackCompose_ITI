package com.dmy.weather.presentation.components

import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import com.dmy.weather.R

@Composable
fun LocationFAB(modifier: Modifier = Modifier, onFabClick: () -> Unit) {
    FloatingActionButton(
        modifier = modifier
            .navigationBarsPadding()
            .padding(end = 16.dp, bottom = 48.dp)
            .size(56.dp),
        containerColor = colorResource(R.color.blue_primary),
        onClick = onFabClick,
        elevation = FloatingActionButtonDefaults.elevation(20.dp)
    ) {
        Icon(
            imageVector = Icons.Default.LocationOn,
            contentDescription = "My Location",
            tint = colorResource(R.color.white),
        )
    }
}

