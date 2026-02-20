package com.dmy.weather.presentation.my_app.top_bar

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.Lifecycle
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.compose.currentBackStackEntryAsState
import com.dmy.weather.presentation.my_app.NavScreens

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppBar(
    navController: NavController,
    scrollBehavior: TopAppBarScrollBehavior,
    bg: Int? = null
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val lifecycleState by navBackStackEntry
        ?.lifecycle
        ?.currentStateFlow
        ?.collectAsState() ?: return

    var currentDestination by remember { mutableStateOf(navBackStackEntry?.destination) }

    LaunchedEffect(lifecycleState) {
        if (lifecycleState >= Lifecycle.State.STARTED) { // changed from RESUMED to STARTED
            currentDestination = navBackStackEntry?.destination
        }
    }

    when {
        currentDestination?.hasRoute<NavScreens.HomeScreen>() == true -> {
            HomeTopBar(navController, scrollBehavior, bg)
        }

        currentDestination?.hasRoute<NavScreens.SettingsScreen>() == true -> {
            DefaultTopBar(
                navController,
                "Settings",
                icon = Icons.Filled.Settings,
                scrollBehavior
            )
        }

        currentDestination?.hasRoute<NavScreens.FavoritesScreen>() == true -> {
            DefaultTopBar(
                navController,
                "Saved Locations",
                icon = Icons.Filled.Favorite,
                scrollBehavior
            )
        }

        currentDestination?.hasRoute<NavScreens.SearchScreen>() == true -> {
            DefaultTopBar(
                navController,
                "Search Location",
                icon = Icons.Filled.Search,
                scrollBehavior
            )
        }

//        currentDestination?.hasRoute<NavScreens.LanguageSelectionScreen>() == true -> {
//            LaunchedEffect(Unit) {
//                scrollBehavior.state.heightOffset = 0f
//                scrollBehavior.state.contentOffset = 0f
//            }
//            SearchAppbar(
//                navController,
//                "Search Location",
//                icon = Icons.Filled.Search,
//                scrollBehavior
//            )
//        }

        else -> {
            ShrinkAppBar()
        }
    }
}

