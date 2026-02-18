import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.dmy.weather.R.color
import com.dmy.weather.R.drawable
import com.dmy.weather.data.model.DailyForecastModel
import com.dmy.weather.data.model.WeatherModel
import com.dmy.weather.presentation.components.MyErrorComponent
import com.dmy.weather.presentation.components.MyLoadingComponent
import com.dmy.weather.presentation.home_screen.UiState


@Composable
fun CurrentWeatherSection(state: UiState<WeatherModel>, dayForecast: DailyForecastModel?) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(bottomStart = 32.dp, bottomEnd = 32.dp))
            .background(
                Brush.linearGradient(
                    colors = listOf(
                        colorResource(color.blue_grad1),
                        colorResource(color.blue_primary),
                        colorResource(color.blue_grad3),
                    )
                )
            )
            .defaultMinSize(minHeight = 200.dp)
            .padding(vertical = 36.dp, horizontal = 24.dp),
    ) {
        CompositionLocalProvider(LocalContentColor provides Color.White) {
            when {
                state.data != null -> WeatherContent(state.data, dayForecast)
                state.isLoading -> MyLoadingComponent(color = color.white)
                state.error != null -> MyErrorComponent(state.error)
            }
        }
    }
}

@Composable
private fun WeatherContent(weather: WeatherModel, dayForecast: DailyForecastModel?) {
    Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {

        LocationInfo(text = "${weather.cityName}, ${weather.country}")
        DateInfo(text = weather.time)

        Spacer(Modifier.height(12.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = weather.temperature,
                fontSize = 64.sp,
                fontWeight = FontWeight.SemiBold,
            )
            AsyncImage(
                model = weather.iconUrl,
                contentDescription = "Weather condition icon",
                modifier = Modifier.size(72.dp)
            )
        }

        Text(
            text = weather.description,
            fontSize = 16.sp,
            fontWeight = FontWeight.SemiBold,
        )

        Spacer(Modifier.height(6.dp))

        val low = dayForecast?.forecasts?.firstOrNull()?.tempMin ?: weather.min
        val high = dayForecast?.forecasts?.firstOrNull()?.tempMax ?: weather.max
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            WeatherStatChip(label = "H", value = high)
            WeatherStatChip(label = "L", value = low)
            WeatherStatChip(label = "Feels", value = weather.feelsLike)
        }
    }
}

@Composable
fun LocationInfo(text: String, modifier: Modifier = Modifier) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        Icon(
            painter = painterResource(drawable.location),
            contentDescription = null,
            modifier = Modifier.size(20.dp),
        )
        Text(
            text = text,
            fontSize = 18.sp,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Composable
fun DateInfo(text: String, modifier: Modifier = Modifier) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        Icon(
            painter = painterResource(drawable.calendar),
            contentDescription = null,
            modifier = Modifier.size(16.dp),
        )
        Text(
            text = text,
            fontSize = 14.sp,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Composable
private fun WeatherStatChip(label: String, value: String) {
    Surface(
        shape = RoundedCornerShape(50),
        color = Color.White.copy(alpha = 0.2f),
        tonalElevation = 50.dp
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(
                text = label,
                fontSize = 11.sp,
                fontWeight = FontWeight.SemiBold
            )
            Text(
                text = value,
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
fun CurrentWeatherSectionPreview() {
    CurrentWeatherSection(UiState(),null)
}