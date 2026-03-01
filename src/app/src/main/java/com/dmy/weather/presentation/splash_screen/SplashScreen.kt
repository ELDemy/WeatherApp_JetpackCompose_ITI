package com.dmy.weather.presentation.splash_screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import com.dmy.weather.platform.notification.NotificationBuilder
import com.dmy.weather.platform.work_manager.AlertScheduler
import com.dmy.weather.presentation.components.MyLoadingComponent
import com.dmy.weather.presentation.my_app.NavScreens
import com.dmy.weather.presentation.permissions.notification.PermissionResult
import com.dmy.weather.presentation.permissions.notification.RequestNotificationPermission

@Composable
fun SplashScreen(navController: NavController, modifier: Modifier = Modifier) {

    val context = LocalContext.current
    var showPermissionRequest by remember { mutableStateOf(true) }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        MyLoadingComponent()
    }

    if (showPermissionRequest) {
        RequestNotificationPermission { result ->
            showPermissionRequest = false
            when (result) {
                PermissionResult.Granted -> {
                    NotificationBuilder.createNotificationChannels(context)
                    AlertScheduler.scheduleWeatherCheck(context)
                }

                PermissionResult.Denied -> {
                    // Explain why you need it
                }

                PermissionResult.PermanentlyDenied -> {
                    // Guide user to System Settings
                }
            }

            navController.navigate(NavScreens.HomeScreen) {
                popUpTo(NavScreens.SplashScreen) {
                    inclusive = true
                }
            }
        }
    }
}