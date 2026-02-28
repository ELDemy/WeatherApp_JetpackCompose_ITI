package com.dmy.weather.presentation.app_bar

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.dmy.weather.R
import com.dmy.weather.presentation.my_app.NavScreens


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeTopBar(
    navController: NavController,
    scrollBehavior: TopAppBarScrollBehavior,
    bg: Int? = null
) {
    Box {
        if (bg != null) {
            Image(
                painter = painterResource(bg),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.matchParentSize()
            )
        }
        TopAppBar(
            scrollBehavior = scrollBehavior,
            expandedHeight = 40.dp,
            colors = TopAppBarDefaults.topAppBarColors(
                scrolledContainerColor = if (bg != null) Color.Transparent else colorResource(R.color.blue_primary),
                containerColor = if (bg != null) Color.Transparent else colorResource(R.color.blue_primary),
                titleContentColor = colorResource(R.color.white),
                navigationIconContentColor = colorResource(R.color.white),
                actionIconContentColor = colorResource(R.color.white),
            ),
            title = {
                Text(stringResource(R.string.Weather))
            },
            navigationIcon = {
                Icon(
                    painter = painterResource(R.drawable.app_icon),
                    contentDescription = "App Icon",
                    modifier = Modifier.size(56.dp),
                    tint = Color.Unspecified
                )
            },
            actions = {
                IconButton(onClick = {
                    navController.navigate(NavScreens.LocationSearchScreen(popOnLocationPicked = "0"))
                }
                ) {
                    Icon(
                        imageVector = Icons.Filled.Search,
                        contentDescription = "Search"
                    )
                }

                IconButton(onClick = {
                    navController.navigate(NavScreens.FavoritesScreen)
                }) {
                    Icon(
                        imageVector = Icons.Filled.Favorite,
                        contentDescription = "Favorite"
                    )
                }

                IconButton(onClick = {
                    navController.navigate(NavScreens.SettingsScreen)
                }) {
                    Icon(
                        imageVector = Icons.Filled.Settings,
                        contentDescription = "Settings"
                    )
                }
            }
        )
    }
}
