package com.dmy.weather.presentation.home_screen.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.dmy.weather.R.color
import com.dmy.weather.data.model.HourlyForecastModel
import com.dmy.weather.data.model.WeatherModel
import com.dmy.weather.presentation.components.MyErrorComponent
import com.dmy.weather.presentation.components.MyLoadingComponent
import com.dmy.weather.presentation.home_screen.UiState

@Composable
fun HourlyForecast(state: UiState<HourlyForecastModel>, weather: WeatherModel?) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 20.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        SectionTitleText("Forecast", modifier = Modifier.padding(horizontal = 24.dp))
        when {
            state.data != null -> HourlyListItems(state.data, weather)
            state.isLoading -> MyLoadingComponent()
            state.error != null -> MyErrorComponent(state.error)
        }
    }
}

@Composable
fun HourlyListItems(
    hourlyForecast: HourlyForecastModel,
    weather: WeatherModel?,
    modifier: Modifier = Modifier
) {
    LazyRow(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 4.dp),
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        contentPadding = PaddingValues(start = 12.dp, end = 24.dp),
    ) {
        if (weather != null) {
            item {
                HourDetailCard(
                    time = "Now",
                    // to remove the temp unit to be like the others
                    temp = weather.temperature.dropLast(1),
                    description = weather.description,
                    clouds = weather.clouds,
                    icon = weather.iconUrl,
                )
            }
        }
        items(hourlyForecast.hourlyItems) { item ->
            HourDetailCard(
                time = item.time,
                temp = item.temperature,
                description = item.description,
                clouds = item.clouds,
                icon = item.iconUrl,
            )
        }
    }
}

@Composable
fun HourDetailCard(
    time: String,
    temp: String,
    clouds: String,
    description: String?,
    icon: String?,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .border(
                width = 1.dp,
                color = colorResource(color.lightBlue_border),
                shape = RoundedCornerShape(16.dp)
            )
            .clip(RoundedCornerShape(16.dp))
            .background(colorResource(color.lightBlue_background))
            .padding(vertical = 12.dp, horizontal = 12.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        Text(
            text = time,
            fontSize = 14.sp,
            color = colorResource(color.text_grey)
        )
        AsyncImage(
            model = icon,
            contentDescription = description,
            modifier = Modifier.size(56.dp)
        )
        Text(
            text = temp,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = colorResource(color.text_primary)
        )
        Text(
            text = clouds,
            fontSize = 11.sp,
            color = colorResource(color.blue_primary)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun HourlyForecastPreview() {
    HourDetailCard(
        time = "Now",
        temp = "28°",
        description = "item.description",
        clouds = "10%",
        icon = "https://openweathermap.org/img/wn/01n@2x.png",
    )
}