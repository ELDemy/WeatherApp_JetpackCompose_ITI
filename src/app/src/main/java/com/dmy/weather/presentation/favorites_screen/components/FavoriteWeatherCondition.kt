package com.dmy.weather.presentation.favorites_screen.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dmy.weather.R
import com.dmy.weather.R.drawable
import com.dmy.weather.data.model.WeatherModel
import com.dmy.weather.presentation.utils.toTemp
import com.dmy.weather.presentation.weather_details_screen.components.WeatherIcon

@Composable
fun FavoriteWeatherCondition(weather: WeatherModel?) {
    if (weather != null) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            WeatherIcon(
                iconRes = weather.icon,
                modifier = Modifier.size(44.dp)
            )
            Spacer(Modifier.height(8.dp))
            Text(
                text = weather.temperature.toTemp(),
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = colorResource(R.color.white)
            )
        }
    } else {
        WeatherIcon(
            iconRes = drawable.icon_03n,
            modifier = Modifier.size(44.dp)
        )
    }
}