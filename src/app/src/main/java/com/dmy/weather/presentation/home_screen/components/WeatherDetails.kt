package com.dmy.weather.presentation.home_screen.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dmy.weather.R.color
import com.dmy.weather.R.drawable
import com.dmy.weather.data.model.WeatherModel
import com.dmy.weather.presentation.home_screen.UiState

@Composable
fun WeatherDetails(state: UiState<WeatherModel>) {
    val weather = state.data

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp, vertical = 20.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = "Weather Details",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = colorResource(color.text_primary)
        )

        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            DetailCard(
                modifier = Modifier.weight(1f),
                title = "Humidity",
                data = weather?.humidity,
                icon = drawable.humidity,
                gradientColors = listOf(
                    color.blue_grad4,
                    color.blue_grad5
                ),
                iconBgColor = colorResource(color.blue_background),
            )
            DetailCard(
                modifier = Modifier.weight(1f),
                title = "Wind Speed",
                data = weather?.windSpeed,
                icon = drawable.wind,
                gradientColors = listOf(
                    color.green_grad1,
                    color.green_grad2
                ),
                iconBgColor = colorResource(color.green_background),
            )
        }

        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            DetailCard(
                modifier = Modifier.weight(1f),
                title = "Pressure",
                data = weather?.pressure,
                icon = drawable.pressure,
                gradientColors = listOf(
                    color.purple_grad1,
                    color.purple_grad2
                ),
                iconBgColor = colorResource(color.purple_background),
            )
            DetailCard(
                modifier = Modifier.weight(1f),
                title = "Clouds",
                data = weather?.clouds,
                icon = drawable.cloud,
                gradientColors = listOf(
                    color.grey_grad1,
                    color.grey_grad2
                ),
                iconBgColor = colorResource(color.grey_background),
            )
        }
    }
}

@Composable
fun DetailCard(
    title: String,
    data: String?,
    icon: Int,
    gradientColors: List<Int>,
    iconBgColor: Color,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .border(
                width = 1.dp,
                color = gradientColors.map { colorResource(it) }.first().copy(alpha = 0.3f),
                shape = RoundedCornerShape(16.dp)
            )
            .clip(RoundedCornerShape(16.dp))
            .background(iconBgColor)
            .padding(16.dp)
    ) {

        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(6.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(44.dp)
                        .clip(CircleShape)
                        .background(Brush.linearGradient(gradientColors.map { colorResource(it) })),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        painter = painterResource(icon),
                        contentDescription = title,
                        tint = colorResource(color.white),
                        modifier = Modifier.size(22.dp)
                    )
                }
                Text(
                    text = title,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Medium,
                    color = colorResource(color.text_grey)
                )
            }

            Text(
                text = data ?: "—",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = colorResource(color.text_primary),
            )
        }
    }
}