@file:OptIn(ExperimentalMaterial3Api::class)

package com.dmy.weather.presentation.settings_screen.language_selection_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.dmy.weather.R
import com.dmy.weather.data.enums.AppLanguage
import com.dmy.weather.presentation.settings_screen.SettingsVM
import org.koin.androidx.compose.koinViewModel


@Composable
fun LanguageSelectionScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
    viewModel: SettingsVM = koinViewModel()
) {
    val GradientStart = colorResource(R.color.blue_primary)
    val GradientEnd = colorResource(R.color.blue_grad1)
    val AmberLight = Color(0xFFFEF3C7)
    val AmberMedium = Color(0xFFFDE68A)
    val AmberDark = Color(0xFF92400E)
    val AmberAccent = Color(0xFFF59E0B)

    val settingsState by viewModel.settingsState.collectAsState()
    var pendingSelection by remember(settingsState.lang) { mutableStateOf(settingsState.lang!!) }

    val filtered by viewModel.filteredLanguages.collectAsState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFFF9FAFB))
    ) {
        LanguagesAppbar(navController, viewModel)
        LazyColumn(
            modifier = Modifier.weight(1f),
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            item {
                Text(
                    "CURRENT LANGUAGE",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.Gray,
                    letterSpacing = 1.sp,
                    modifier = Modifier.padding(horizontal = 4.dp)
                )
                Spacer(Modifier.height(8.dp))
                Surface(
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Box(
                        Modifier.background(
                            Brush.horizontalGradient(listOf(GradientStart, GradientEnd))
                        )
                    ) {
                        Row(
                            modifier = Modifier.padding(16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                pendingSelection.flagEmoji,
                                fontSize = 28.sp
                            )
                            Spacer(Modifier.width(12.dp))
                            Column(Modifier.weight(1f)) {
                                Text(
                                    pendingSelection.displayName,
                                    color = Color.White,
                                    fontWeight = FontWeight.SemiBold,
                                    fontSize = 16.sp
                                )
                                Text(
                                    pendingSelection.apiCode,
                                    color = Color.White.copy(alpha = 0.8f),
                                    fontSize = 12.sp,
                                    fontFamily = FontFamily.Monospace
                                )
                            }
                            Box(
                                modifier = Modifier
                                    .size(32.dp)
                                    .clip(CircleShape)
                                    .background(Color.White.copy(alpha = 0.3f)),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    Icons.Default.Check,
                                    contentDescription = null,
                                    tint = Color.White
                                )
                            }
                        }
                    }
                }
            }

            // ── All Languages ────────────────────────────────────────────────
            item {
                Text(
                    "ALL LANGUAGES (${filtered.size})",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.Gray,
                    letterSpacing = 1.sp,
                    modifier = Modifier.padding(horizontal = 4.dp)
                )
                Spacer(Modifier.height(8.dp))
            }

            item {
                Surface(
                    shape = RoundedCornerShape(16.dp),
                    color = Color.White,
                    shadowElevation = 1.dp
                ) {
                    Column {
                        filtered.forEachIndexed { index, lang ->
                            val isSelected = pendingSelection == lang
                            val isRtl = lang.isRtl
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable { pendingSelection = lang }
                                    .background(if (isSelected) AmberLight else Color.Transparent)
                                    .padding(horizontal = 16.dp, vertical = 14.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(lang.flagEmoji, fontSize = 22.sp)
                                Spacer(Modifier.width(12.dp))
                                Column(Modifier.weight(1f)) {
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Text(
                                            lang.displayName,
                                            fontWeight = FontWeight.Medium,
                                            color = if (isSelected) AmberDark else Color(0xFF111827),
                                            fontSize = 15.sp
                                        )
                                        if (isRtl) {
                                            Spacer(Modifier.width(6.dp))
                                            Surface(
                                                shape = RoundedCornerShape(50),
                                                color = Color(0xFFDBEAFE)
                                            ) {
                                                Text(
                                                    "RTL",
                                                    color = Color(0xFF2563EB),
                                                    fontSize = 10.sp,
                                                    fontWeight = FontWeight.Bold,
                                                    modifier = Modifier.padding(
                                                        horizontal = 6.dp,
                                                        vertical = 2.dp
                                                    )
                                                )
                                            }
                                        }
                                    }
                                    Text(
                                        lang.apiCode,
                                        color = Color.Gray,
                                        fontSize = 12.sp,
                                        fontFamily = FontFamily.Monospace
                                    )
                                }
                                if (isSelected) {
                                    Box(
                                        modifier = Modifier
                                            .size(28.dp)
                                            .clip(CircleShape)
                                            .background(AmberAccent),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Icon(
                                            Icons.Default.Check,
                                            contentDescription = null,
                                            tint = Color.White,
                                            modifier = Modifier.size(16.dp)
                                        )
                                    }
                                } else {
                                    Box(
                                        modifier = Modifier
                                            .size(28.dp)
                                            .clip(CircleShape)
                                            .background(Color.Transparent)
                                            .then(
                                                Modifier.border(
                                                    2.dp, Color.LightGray, CircleShape
                                                )
                                            )
                                    )
                                }
                            }
                            if (index < filtered.lastIndex) {
                                HorizontalDivider(color = Color(0xFFF3F4F6), thickness = 1.dp)
                            }
                        }
                    }
                }
            }
        }

        // ── Save Button ──────────────────────────────────────────────────────
        SaveButton(navController, viewModel, pendingSelection)
    }
}

@Composable
fun SaveButton(
    navController: NavController,
    viewModel: SettingsVM,
    pendingSelection: AppLanguage
) {
    val GradientStart = colorResource(R.color.blue_primary)
    val GradientEnd = colorResource(R.color.blue_grad1)
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .padding(16.dp)
    ) {
        Button(
            onClick = {
                viewModel.updateLanguage(pendingSelection) // adjust to your VM method
                navController.popBackStack()
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp),
            shape = RoundedCornerShape(16.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
            contentPadding = PaddingValues(0.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Brush.horizontalGradient(listOf(GradientStart, GradientEnd))),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    "Save Language",
                    color = Color.White,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 16.sp
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LanguageSelectionScreenPreview() {
    LanguageSelectionScreen(navController = rememberNavController())
}