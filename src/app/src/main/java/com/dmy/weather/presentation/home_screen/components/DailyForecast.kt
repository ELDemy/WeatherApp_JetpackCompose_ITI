package com.dmy.weather.presentation.home_screen.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.dmy.weather.R
import com.dmy.weather.R.color
import com.dmy.weather.data.model.DailyForecastModel
import com.dmy.weather.data.model.DailyWeatherModel
import com.dmy.weather.presentation.components.MyErrorComponent
import com.dmy.weather.presentation.components.MyLoadingComponent
import com.dmy.weather.presentation.home_screen.UiState

@Composable
fun DailyForecast(state: UiState<DailyForecastModel>) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 20.dp, horizontal = 24.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        SectionTitleText(stringResource(R.string.Next_Days_Forecast))
        when {
            state.data != null -> DailyListItems(state.data)
            state.isLoading -> MyLoadingComponent()
            state.error != null -> MyErrorComponent(state.error)
        }
    }
}

@Composable
fun DailyListItems(dailyForecastModel: DailyForecastModel) {
    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
        dailyForecastModel.forecasts.forEachIndexed { index, it ->
            DailyForecastItem(it, index)
        }
    }
}

@Composable
fun DailyForecastItem(weather: DailyWeatherModel, index: Int) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .border(
                width = 1.dp,
                color = colorResource(color.lightBlue_border),
                shape = RoundedCornerShape(20.dp)
            )
            .clip(RoundedCornerShape(20.dp))
            .background(colorResource(color.lightBlue_background))
            .padding(vertical = 12.dp, horizontal = 14.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        AsyncImage(
            model = weather.icon,
            contentDescription = weather.description,
            modifier = Modifier.size(48.dp)
        )

        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(2.dp)
        ) {
            Text(
                text = if (index == 0) stringResource(R.string.Today) else weather.dateTime,
                fontSize = 13.sp,
                fontWeight = FontWeight.SemiBold,
                color = colorResource(color.text_primary)
            )
            Text(
                text = weather.description,
                fontSize = 11.sp,
                fontWeight = FontWeight.Normal,
                color = colorResource(color.text_grey)
            )
        }

        // Temp range pill
        Column(
            modifier = Modifier
                .clip(RoundedCornerShape(50.dp))
                .background(
                    Brush.linearGradient(
                        colors = listOf(
                            colorResource(color.orange_grad1),
                            colorResource(color.blue_grad4),
                        )
                    )
                )
                .padding(horizontal = 6.dp, vertical = 4.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                text = weather.tempMax,
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold,
                color = colorResource(color.white)
            )
            Text(
                text = "—",
                fontSize = 10.sp,
                lineHeight = 1.sp,
                color = colorResource(color.white).copy(alpha = 0.5f)
            )
            Text(
                text = weather.tempMin,
                fontSize = 13.sp,
                fontWeight = FontWeight.Medium,
                color = colorResource(color.white).copy(alpha = 0.85f)
            )
        }
    }
}