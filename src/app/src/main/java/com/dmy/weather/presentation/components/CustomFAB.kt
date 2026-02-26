package com.dmy.weather.presentation.components

import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import com.dmy.weather.R

@Composable
fun CustomFAB(
    modifier: Modifier = Modifier,
    icon: ImageVector,
    onFabClick: () -> Unit
) {
    FloatingActionButton(
        modifier = modifier
            .navigationBarsPadding()
            .padding(end = 28.dp, bottom = 48.dp)
            .size(56.dp),
        containerColor = colorResource(R.color.blue_primary),
        onClick = onFabClick,
        elevation = FloatingActionButtonDefaults.elevation(20.dp)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = "My Location",
            tint = colorResource(R.color.white),
        )
    }
}

