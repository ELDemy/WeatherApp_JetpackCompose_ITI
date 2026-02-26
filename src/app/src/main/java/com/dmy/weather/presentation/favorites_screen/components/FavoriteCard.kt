package com.dmy.weather.presentation.favorites_screen.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import com.dmy.weather.R
import com.dmy.weather.data.model.CityModel
import com.dmy.weather.data.model.WeatherModel
import com.dmy.weather.presentation.components.WeatherBackground
import com.dmy.weather.presentation.favorites_screen.FavoritesVM
import org.koin.androidx.compose.koinViewModel

private const val TAG = "FavoriteCard"

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavoriteCard(
    city: CityModel,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val viewModel: FavoritesVM = koinViewModel()
    var weather by remember { mutableStateOf<WeatherModel?>(null) }

    LaunchedEffect(city.latitude, city.longitude, city.name) {
        viewModel.getWeather(
            city.longitude.toString(),
            city.latitude.toString(),
            onWeatherLoaded = { weather = it }
        )
    }

    val dismissState = rememberSwipeToDismissBoxState()
    val isDismissed = dismissState.currentValue == SwipeToDismissBoxValue.EndToStart

    val alpha by animateFloatAsState(
        targetValue = if (isDismissed) 0f else 1f,
        animationSpec = tween(durationMillis = 400),
        label = "cardFadeOut"
    )

    SwipeToDismissBox(
        state = dismissState,
        modifier = modifier
            .fillMaxWidth()
            .alpha(alpha),
        enableDismissFromStartToEnd = false,
        enableDismissFromEndToStart = true,
        backgroundContent = { DeleteBackground() },
        onDismiss = { viewModel.removeFromFav(city) }
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(20.dp))
                .clickable { onClick() }
                .height(120.dp)
        ) {
            WeatherBackground(weather?.bg, Modifier.matchParentSize())

            Row(
                modifier = Modifier
                    .matchParentSize()
                    .padding(horizontal = 20.dp, vertical = 14.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                FavCityInfo(city, weather?.description, Modifier.weight(1f))

                Spacer(Modifier.width(12.dp))

                FavoriteWeatherCondition(weather)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun DeleteBackground() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .clip(RoundedCornerShape(20.dp))
            .background(colorResource(R.color.error_foreground)),
        contentAlignment = Alignment.CenterEnd
    ) {
        Icon(
            imageVector = Icons.Filled.Delete,
            contentDescription = "Delete",
            tint = colorResource(R.color.white),
            modifier = Modifier
                .padding(end = 24.dp)
                .size(28.dp)
        )
    }
}