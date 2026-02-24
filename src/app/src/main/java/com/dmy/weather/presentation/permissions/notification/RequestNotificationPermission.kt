package com.dmy.weather.presentation.permissions.notification

import android.Manifest
import android.app.Activity
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import com.dmy.weather.utils.NotificationUtils

@Composable
fun RequestNotificationPermission(onResult: (PermissionResult) -> Unit) {
    val context = LocalContext.current
    val activity = context as? Activity

    // Check if we already have permission (Utility function used below)
    var hasPermission by remember {
        mutableStateOf(NotificationUtils.hasNotificationPermission(context))
    }

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU && !hasPermission) {
        val launcher = rememberLauncherForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted ->
            if (isGranted) {
                hasPermission = true
                onResult(PermissionResult.Granted)
            } else {
                val permanentlyDenied = activity?.shouldShowRequestPermissionRationale(
                    Manifest.permission.POST_NOTIFICATIONS
                ) == false

                onResult(
                    if (permanentlyDenied) PermissionResult.PermanentlyDenied
                    else PermissionResult.Denied
                )
            }
        }

        LaunchedEffect(Unit) {
            launcher.launch(Manifest.permission.POST_NOTIFICATIONS)
        }
    } else {
        // If below Android 13 or already granted
        LaunchedEffect(Unit) {
            onResult(PermissionResult.Granted)
        }
    }
}