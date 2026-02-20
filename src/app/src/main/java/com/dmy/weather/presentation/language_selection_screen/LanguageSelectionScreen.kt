@file:OptIn(ExperimentalMaterial3Api::class)

package com.dmy.weather.presentation.language_selection_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.dmy.weather.R
import com.dmy.weather.presentation.language_selection_screen.components.LanguageList
import com.dmy.weather.presentation.language_selection_screen.components.LanguagesAppbar
import com.dmy.weather.presentation.language_selection_screen.components.SaveButton
import com.dmy.weather.presentation.language_selection_screen.components.SectionLabel
import com.dmy.weather.presentation.language_selection_screen.components.SelectedLanguageCard
import com.dmy.weather.presentation.settings_screen.SettingsVM
import org.koin.androidx.compose.koinViewModel

@Composable
fun LanguageSelectionScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
    viewModel: SettingsVM = koinViewModel()
) {
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
                SectionLabel(stringResource(R.string.Selected_Language))
                Spacer(Modifier.height(8.dp))
                SelectedLanguageCard(pendingSelection)
            }

            item {
                SectionLabel("${stringResource(R.string.All_Languages)} (${filtered.size})")
            }

            item {
                LanguageList(
                    languages = filtered,
                    selected = pendingSelection,
                    onSelect = { pendingSelection = it }
                )
            }
        }

        SaveButton(navController, viewModel, pendingSelection)
    }
}


@Preview(showBackground = true)
@Composable
fun LanguageSelectionScreenPreview() {
    LanguageSelectionScreen(navController = rememberNavController())
}