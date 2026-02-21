package com.dmy.weather.presentation.location_picker_screen.component

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import com.dmy.weather.R

@Composable
fun ConfirmButton(enabled: Boolean, modifier: Modifier = Modifier, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = modifier.padding(16.dp),
        enabled = enabled,
        colors = ButtonDefaults.buttonColors(
            containerColor = colorResource(R.color.blue_primary),
            contentColor = colorResource(R.color.white),
        ),
    ) {
        Text("Confirm Location")
    }
}