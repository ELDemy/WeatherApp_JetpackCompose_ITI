package com.dmy.weather.presentation.settings_screen.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun SettingsCard(
    headerColor: Color,
    headerBorderColor: Color,
    header: @Composable RowScope.() -> Unit,
    content: @Composable ColumnScope.() -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(headerColor)
                .drawBottomBorder(headerBorderColor)
                .padding(horizontal = 16.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
            content = header
        )
        Column(modifier = Modifier.padding(16.dp), content = content)
    }
}

fun Modifier.drawBottomBorder(color: Color) = this.drawWithContent {
    drawContent()
    drawLine(
        color = color,
        start = androidx.compose.ui.geometry.Offset(0f, size.height),
        end = androidx.compose.ui.geometry.Offset(size.width, size.height),
        strokeWidth = 1.dp.toPx()
    )
}
