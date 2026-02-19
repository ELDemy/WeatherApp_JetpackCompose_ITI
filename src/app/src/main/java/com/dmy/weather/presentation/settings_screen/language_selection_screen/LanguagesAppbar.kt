package com.dmy.weather.presentation.settings_screen.language_selection_screen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.dmy.weather.R
import com.dmy.weather.presentation.settings_screen.SettingsVM

@Composable
fun LanguagesAppbar(navController: NavController, viewModel: SettingsVM) {
    val GradientStart = colorResource(R.color.blue_primary)
    val GradientEnd = colorResource(R.color.blue_grad1)

    val search by viewModel.searchQuery.collectAsState()

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Brush.horizontalGradient(listOf(GradientStart, GradientEnd)))
            .padding(horizontal = 16.dp, vertical = 12.dp)
    ) {
        Column {
            Row(verticalAlignment = Alignment.CenterVertically) {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(
                        Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back",
                        tint = Color.White
                    )
                }
                Spacer(Modifier.width(8.dp))
                Text(
                    "Select Language",
                    color = Color.White,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 18.sp
                )
            }
            Spacer(Modifier.height(10.dp))
            // Search bar
            Surface(
                shape = RoundedCornerShape(50),
                color = Color.White,
                shadowElevation = 2.dp,
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp)
                ) {
                    Icon(Icons.Default.Search, contentDescription = null, tint = Color.Gray)
                    Spacer(Modifier.width(8.dp))
                    BasicTextField(
                        value = search,
                        onValueChange = { viewModel.updateSearch(it) },
                        singleLine = true,
                        modifier = Modifier.weight(1f),
                        decorationBox = { inner ->
                            if (search.isEmpty()) Text(
                                "Search languages...",
                                color = Color.Gray
                            )
                            inner()
                        }
                    )
                    AnimatedVisibility(search.isNotEmpty()) {
                        IconButton(
                            onClick = { viewModel.updateSearch("") },
                            modifier = Modifier.size(32.dp)
                        ) {
                            Icon(
                                Icons.Default.Clear,
                                contentDescription = "Clear",
                                tint = Color.Gray
                            )
                        }
                    }
                }
            }
        }
    }
}