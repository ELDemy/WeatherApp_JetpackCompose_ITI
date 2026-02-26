package com.dmy.weather.presentation.search_screen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForwardIos
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.Warning
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.dmy.weather.R
import com.dmy.weather.data.enums.AppLanguage
import com.dmy.weather.data.model.LocationDetails
import com.dmy.weather.presentation.components.CustomFAB
import com.dmy.weather.presentation.my_app.NavScreens
import kotlinx.coroutines.FlowPreview
import org.koin.androidx.compose.koinViewModel

private const val TAG = "SearchScreen"

@OptIn(FlowPreview::class)
@Composable
fun SearchScreen(
    navController: NavController,
    modifier: Modifier,
    viewModel: SearchVM = koinViewModel()
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    val searchQuery by viewModel.searchQuery.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        navController.currentBackStackEntry
            ?.savedStateHandle
            ?.getStateFlow<LocationDetails?>("picked_location", null)
            ?.collect { location ->
                if (location != null) {
                    navController.currentBackStackEntry
                        ?.savedStateHandle
                        ?.remove<LocationDetails>("picked_location")
                    navController.navigate(
                        NavScreens.WeatherScreen(lat = location.lat, long = location.long)
                    )
                }
            }
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(colorResource(R.color.white_background))
    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 16.dp)
        ) {

            Text(
                text = "Search Location",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = colorResource(R.color.text_primary),
                modifier = Modifier.padding(bottom = 12.dp)
            )

            Surface(
                shape = RoundedCornerShape(16.dp),
                color = colorResource(R.color.white),
                shadowElevation = 4.dp,
                modifier = Modifier.fillMaxWidth()
            ) {
                TextField(
                    value = searchQuery,
                    onValueChange = { viewModel.onQueryChanged(it) },
                    modifier = Modifier.fillMaxWidth(),
                    placeholder = {
                        Text(
                            "City name, country…",
                            color = colorResource(R.color.text_grey),
                            fontSize = 15.sp
                        )
                    },
                    leadingIcon = {
                        Icon(
                            Icons.Default.Search,
                            contentDescription = null,
                            tint = colorResource(R.color.blue_primary),
                            modifier = Modifier.size(22.dp)
                        )
                    },
                    trailingIcon = {
                        AnimatedVisibility(
                            visible = searchQuery.isNotEmpty(),
                            enter = fadeIn() + scaleIn(),
                            exit = fadeOut() + scaleOut()
                        ) {
                            IconButton(onClick = { viewModel.clearSearch() }) {
                                Icon(
                                    Icons.Default.Close,
                                    contentDescription = "Clear",
                                    tint = colorResource(R.color.blue_primary),
                                    modifier = Modifier.size(18.dp)
                                )
                            }
                        }
                    },
                    singleLine = true,
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.Transparent,
                        unfocusedContainerColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        cursorColor = colorResource(R.color.blue_primary),
                        focusedTextColor = colorResource(R.color.text_primary),
                        unfocusedTextColor = colorResource(R.color.text_primary),
                    )
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            AnimatedVisibility(
                visible = !state.suggestions.isNullOrEmpty(),
                enter = fadeIn() + expandVertically(),
                exit = fadeOut() + shrinkVertically()
            ) {
                Surface(
                    shape = RoundedCornerShape(16.dp),
                    color = colorResource(R.color.white),
                    shadowElevation = 6.dp,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxWidth()
                            .heightIn(max = 360.dp)
                    ) {
                        itemsIndexed(state.suggestions.orEmpty()) { index, city ->
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable {
                                        city.name.let {
                                            navController.navigate(NavScreens.WeatherScreen(it))
                                        }
                                    }
                                    .padding(horizontal = 16.dp, vertical = 14.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(42.dp)
                                        .background(
                                            color = colorResource(R.color.white),
                                            shape = CircleShape
                                        ),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = AppLanguage.getFlag(city.country) ?: "🌐",
                                        fontSize = 20.sp
                                    )
                                }

                                Spacer(modifier = Modifier.width(14.dp))

                                Column(modifier = Modifier.weight(1f)) {
                                    Text(
                                        text = city.name,
                                        fontSize = 15.sp,
                                        fontWeight = FontWeight.SemiBold,
                                        color = colorResource(R.color.text_primary),
                                    )
                                    Text(
                                        text = city.country,
                                        fontSize = 12.sp,
                                        color = colorResource(R.color.text_grey),
                                        modifier = Modifier.padding(top = 2.dp)
                                    )
                                }

                                Icon(
                                    imageVector = Icons.AutoMirrored.Filled.ArrowForwardIos,
                                    contentDescription = null,
                                    tint = colorResource(R.color.blue_primary),
                                    modifier = Modifier.size(14.dp)
                                )
                            }

                            if (index < (state.suggestions?.size ?: 0) - 1) {
                                HorizontalDivider(
                                    modifier = Modifier.padding(horizontal = 16.dp),
                                    color = colorResource(R.color.text_grey),
                                    thickness = 1.dp
                                )
                            }
                        }
                    }
                }
            }

            if (searchQuery.isNotEmpty() && state.suggestions.isNullOrEmpty() && !state.isLoading) {
                Spacer(modifier = Modifier.height(48.dp))
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text("🔍", fontSize = 40.sp)
                    Spacer(Modifier.height(12.dp))
                    Text(
                        "No results for \"$searchQuery\"",
                        color = colorResource(R.color.text_grey),
                        fontSize = 14.sp,
                        textAlign = TextAlign.Center
                    )
                }
            }

            if (state.error != null) {
                Spacer(modifier = Modifier.height(12.dp))
                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = colorResource(R.color.white),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier.padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            Icons.Outlined.Warning,
                            contentDescription = null,
                            tint = colorResource(R.color.error_foreground),
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(Modifier.width(8.dp))
                        Text(
                            text = state.error.orEmpty(),
                            color = colorResource(R.color.error_foreground),
                            fontSize = 13.sp
                        )
                    }
                }
            }
        }

        CustomFAB(
            modifier = Modifier
                .align(Alignment.BottomEnd),
            icon = Icons.Default.LocationOn,
            onFabClick = { navController.navigate(NavScreens.LocationPickerScreen()) }
        )
    }
}