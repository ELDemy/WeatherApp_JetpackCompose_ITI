package com.dmy.weather.presentation.weather_details_screen.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dmy.weather.R
import com.dmy.weather.R.color
import com.dmy.weather.data.model.HourlyForecastModel
import com.dmy.weather.data.model.WeatherModel
import com.dmy.weather.presentation.components.MyErrorComponent
import com.dmy.weather.presentation.components.MyLoadingComponent
import com.dmy.weather.presentation.utils.toClouds
import com.dmy.weather.presentation.utils.toTemp
import com.dmy.weather.presentation.weather_details_screen.UiState

@Composable
fun HourlyForecast(state: UiState<HourlyForecastModel>, weather: WeatherModel?) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 20.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        SectionTitleText(
            stringResource(R.string.Forecast),
            modifier = Modifier.padding(horizontal = 24.dp)
        )
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
                    time = stringResource(R.string.Now),
                    temp = weather.temperature.toTemp(),
                    description = weather.description,
                    clouds = weather.clouds.toClouds(),
                    icon = weather.icon,
                    bg = weather.bg,
                )
            }
        }
        items(hourlyForecast.hourlyItems) { item ->
            HourDetailCard(
                time = item.time,
                temp = item.temperature.toTemp(),
                description = item.description,
                clouds = item.clouds.toClouds(),
                icon = item.icon,
                bg = item.bg
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
    icon: Int?,
    bg: Int?,
    modifier: Modifier = Modifier
) {
    WeatherCardBackground(
        bg = bg,
        modifier = modifier
            .border(
                width = 1.dp,
                color = colorResource(color.lightBlue_border),
                shape = RoundedCornerShape(16.dp)
            )
            .clip(RoundedCornerShape(16.dp))
    ) {
        Column(
            modifier = Modifier.padding(vertical = 12.dp, horizontal = 12.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Text(text = time, fontSize = 14.sp, color = colorResource(color.text_grey))
            WeatherIcon(iconRes = icon, modifier = Modifier.size(56.dp))
            Spacer(Modifier.height(12.dp))
            Text(
                text = temp,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = colorResource(color.text_primary)
            )
            Text(text = clouds, fontSize = 11.sp, color = colorResource(color.blue_primary))
        }
    }
}

@Composable
fun WeatherCardBackground(
    bg: Int?,
    modifier: Modifier = Modifier,
    fallbackColor: Color = colorResource(color.lightBlue_background),
    content: @Composable BoxScope.() -> Unit
) {
    Box(modifier = modifier) {
        if (bg != null) {
            Image(
                painter = painterResource(id = bg),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.matchParentSize()
            )
        } else {
            Box(
                modifier = Modifier
                    .matchParentSize()
                    .background(fallbackColor)
            )
        }
        content()
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
        icon = R.drawable.icon_02n,
        bg = R.drawable.bg_02n
    )
}