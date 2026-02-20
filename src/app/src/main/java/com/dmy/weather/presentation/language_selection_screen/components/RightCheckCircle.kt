package com.dmy.weather.presentation.language_selection_screen.components

import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.dmy.weather.R
import com.dmy.weather.presentation.components.CheckCircle


@Composable
fun RightCheckCircle(
    filled: Boolean,
    modifier: Modifier = Modifier,
    size: Dp = 28.dp
) {
    CheckCircle(
        filled = filled,
        modifier = modifier,
        checkedIcon = {
            Icon(
                Icons.Default.Check,
                contentDescription = null,
                tint = colorResource(R.color.white),
                modifier = Modifier.size(size * 0.57f)
            )
        },
    )
}
