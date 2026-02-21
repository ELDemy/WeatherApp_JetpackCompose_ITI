@file:OptIn(ExperimentalMaterial3Api::class)

package com.dmy.weather.presentation.app_bar


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
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.dmy.weather.R
import com.dmy.weather.presentation.settings_screen.SettingsVM
import org.koin.androidx.compose.koinViewModel

@Composable
fun SearchAppbar(
    navController: NavController,
    title: String,
    icon: ImageVector,
    scrollBehavior: TopAppBarScrollBehavior?,
    settingsVM: SettingsVM = koinViewModel()
) {
    TopAppBar(
        scrollBehavior = scrollBehavior,
        colors = TopAppBarDefaults.topAppBarColors(
            scrolledContainerColor = colorResource(R.color.blue_primary),
            containerColor = colorResource(R.color.blue_primary),
            titleContentColor = colorResource(R.color.white),
            navigationIconContentColor = colorResource(R.color.white),
            actionIconContentColor = colorResource(R.color.white),
        ),
        title = {
            SearchBar(navController, settingsVM)
        }

    )
}

@Composable
fun SearchBar(navController: NavController, settingsVM: SettingsVM) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                Brush.horizontalGradient(
                    listOf(
                        colorResource(R.color.blue_primary),
                        colorResource(R.color.blue_grad1)
                    )
                )
            )
            .padding(horizontal = 16.dp, vertical = 12.dp)
    ) {
        Column(Modifier.fillMaxWidth()) {
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

            SearchTextField(settingsVM)

        }
    }
}

@Composable
fun SearchTextField(viewModel: SettingsVM) {
    val search by viewModel.searchQuery.collectAsState()

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
                        fontSize = 14.sp,
                        color = Color.Gray
                    )
                    inner()
                }
            )
            if (search.isNotEmpty()) IconButton(
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

@Preview(showBackground = true)
@Composable
fun SearchAppbarPreview() {
    SearchAppbar(
        rememberNavController(),
        "Settings",
        icon = Icons.Filled.Settings,
        null,
    )
}
