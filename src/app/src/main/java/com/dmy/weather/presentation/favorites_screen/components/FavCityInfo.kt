package com.dmy.weather.presentation.favorites_screen.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dmy.weather.R
import com.dmy.weather.R.drawable
import com.dmy.weather.data.model.CityModel

@Composable
fun FavCityInfo(city: CityModel, description: String?, modifier: Modifier) {
    Column(
        verticalArrangement = Arrangement.spacedBy(4.dp),
        modifier = modifier
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Icon(
                painter = painterResource(drawable.location),
                contentDescription = null,
                modifier = Modifier.size(16.dp),
                tint = colorResource(R.color.white)
            )
            Text(
                text = city.name,
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold,
                color = colorResource(R.color.white),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }

        Text(
            text = buildString {
                append(city.country)
                if (city.state.isNotBlank()) append(", ${city.state}")
            },
            fontSize = 13.sp,
            color = colorResource(R.color.text_white),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )

        description?.let {
            Text(
                text = it,
                fontSize = 13.sp,
                color = colorResource(R.color.text_white),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}
