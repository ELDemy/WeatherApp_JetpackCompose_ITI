package com.dmy.weather.presentation.favorites_screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.dmy.weather.presentation.components.MyErrorComponent
import com.dmy.weather.presentation.components.MyLoadingComponent
import com.dmy.weather.presentation.favorites_screen.components.FavoritesList
import org.koin.androidx.compose.koinViewModel

@Composable
fun FavoritesScreen(navController: NavController, modifier: Modifier = Modifier) {
    val viewModel: FavoritesVM = koinViewModel()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) { viewModel.loadFavoriteCities() }

    Column(
        modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        when {
            uiState.data != null -> FavoritesList(navController, uiState.data!!)
            uiState.isLoading -> MyLoadingComponent()
            uiState.error != null -> MyErrorComponent(uiState.error!!)
        }
    }
}
