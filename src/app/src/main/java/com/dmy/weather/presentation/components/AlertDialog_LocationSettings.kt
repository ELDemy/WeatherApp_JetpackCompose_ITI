package com.dmy.weather.presentation.components

import android.content.Intent
import android.net.Uri
import android.provider.Settings
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.LocationOff
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.platform.LocalContext

@Composable
fun AlertDialogForLocationSettings(showLocationDialog: MutableState<Boolean>) {
    val context = LocalContext.current
    AlertDialog(
        onDismissRequest = { showLocationDialog.value = false },
        icon = { Icon(Icons.Outlined.LocationOff, contentDescription = null) },
        title = { Text("Location is Off") },
        text = { Text("Please turn on location services to get weather for your current location.") },
        confirmButton = {
            TextButton(onClick = {
                showLocationDialog.value = false
                context.startActivity(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS))
            }) { Text("Open Settings") }
        },
        dismissButton = {
            TextButton(onClick = { showLocationDialog.value = false }) { Text("Cancel") }
        }
    )
}

@Composable
fun AlertDialogForLocationPermission(showDialog: MutableState<Boolean>) {
    val context = LocalContext.current
    AlertDialog(
        onDismissRequest = { showDialog.value = false },
        icon = { Icon(Icons.Outlined.LocationOff, contentDescription = null) },
        title = { Text("Location is Off") },
        text = { Text("Please turn on location services to get weather for your current location.") },
        confirmButton = {
            TextButton(onClick = {
                showDialog.value = false
                context.startActivity(
                    Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                        data = Uri.fromParts("package", context.packageName, null)
                    }
                )
            }) { Text("Open Settings") }
        },
        dismissButton = {
            TextButton(onClick = { showDialog.value = false }) { Text("Cancel") }
        }
    )
}