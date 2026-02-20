package com.dmy.weather.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.dmy.weather.R


@Composable
fun CheckCircle(
    filled: Boolean,
    modifier: Modifier = Modifier,
    checkedIcon: @Composable (size: Dp) -> Unit,
    size: Dp = 28.dp,
) {
    if (filled) {
        Box(
            modifier = modifier
                .size(size)
                .clip(CircleShape)
                .background(colorResource(R.color.white).copy(0.3f)),
            contentAlignment = Alignment.Center
        ) {
            checkedIcon(size)
        }
    } else {
        UnCheckCircle(size, modifier)
    }
}

@Composable
fun UnCheckCircle(size: Dp, modifier: Modifier) {
    Box(
        modifier = modifier
            .size(size)
            .clip(CircleShape)
            .border(2.dp, Color.LightGray, CircleShape)
    )
}
