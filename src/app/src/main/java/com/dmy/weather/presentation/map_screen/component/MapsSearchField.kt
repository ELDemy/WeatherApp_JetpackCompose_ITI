package com.dmy.weather.presentation.map_screen.component

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.AutocompletePrediction
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.api.net.FetchPlaceRequest
import com.google.android.libraries.places.api.net.FindAutocompletePredictionsRequest
import com.google.maps.android.compose.CameraPositionState
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.launch
import kotlinx.coroutines.suspendCancellableCoroutine


@Composable
fun MapsSearchField(
    showSuggestions: MutableState<Boolean>,
    cameraPositionState: CameraPositionState,
    pickedLocation: MutableState<LatLng?>,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    var searchQuery by remember { mutableStateOf("") }
    var suggestions by remember { mutableStateOf<List<AutocompletePrediction>>(emptyList()) }
    val placesClient = remember { Places.createClient(context) }
    val coroutineScope = rememberCoroutineScope()

    suspend fun fetchSuggestions(query: String): List<AutocompletePrediction> {
        return suspendCancellableCoroutine { continuation ->
            val request = FindAutocompletePredictionsRequest.builder()
                .setQuery(query)
                .build()
            placesClient.findAutocompletePredictions(request)
                .addOnSuccessListener { response ->
                    Log.d("Places", "Got ${response.autocompletePredictions.size} suggestions")
                    continuation.resume(response.autocompletePredictions, onCancellation = {})
                }
                .addOnFailureListener { exception ->
                    Log.e("Places", "Autocomplete error: ${exception.message}", exception)
                    continuation.resume(emptyList(), onCancellation = {})
                }
        }
    }

    LaunchedEffect(Unit) {
        snapshotFlow { searchQuery }
            .debounce(300)
            .collectLatest { query ->
                if (query.length < 3) {
                    suggestions = emptyList()
                    showSuggestions.value = false
                    return@collectLatest
                }
                Log.d("Places", "Searching for: $query")
                suggestions = fetchSuggestions(query)
                showSuggestions.value = suggestions.isNotEmpty()
            }
    }

    Column(
        modifier
            .fillMaxWidth()
            .padding(12.dp)
    ) {
        OutlinedTextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            modifier = Modifier.fillMaxWidth(),
            placeholder = { Text("Search location…") },
            leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
            trailingIcon = {
                if (searchQuery.isNotEmpty()) {
                    IconButton(onClick = {
                        searchQuery = ""
                        suggestions = emptyList()
                        showSuggestions.value = false
                    }) {
                        Icon(Icons.Default.Close, contentDescription = "Clear")
                    }
                }
            },
            singleLine = true,
            shape = RoundedCornerShape(12.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = MaterialTheme.colorScheme.surface,
                unfocusedContainerColor = MaterialTheme.colorScheme.surface
            )
        )

        if (showSuggestions.value && suggestions.isNotEmpty()) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(bottomStart = 12.dp, bottomEnd = 12.dp),
                elevation = CardDefaults.cardElevation(8.dp)
            ) {
                LazyColumn {
                    items(suggestions) { prediction ->
                        ListItem(
                            headlineContent = {
                                Text(prediction.getPrimaryText(null).toString())
                            },
                            supportingContent = {
                                Text(
                                    prediction.getSecondaryText(null).toString(),
                                    style = MaterialTheme.typography.bodySmall
                                )
                            },
                            modifier = Modifier.clickable {
                                coroutineScope.launch {
                                    val placeFields =
                                        listOf(Place.Field.LOCATION, Place.Field.DISPLAY_NAME)
                                    val fetchRequest = FetchPlaceRequest.newInstance(
                                        prediction.placeId, placeFields
                                    )
                                    suspendCancellableCoroutine { continuation ->
                                        placesClient.fetchPlace(fetchRequest)
                                            .addOnSuccessListener { result ->
                                                result.place.location?.let { latLng ->
                                                    pickedLocation.value = latLng
                                                    cameraPositionState.move(
                                                        CameraUpdateFactory.newLatLngZoom(
                                                            latLng,
                                                            15f
                                                        )
                                                    )
                                                }
                                                searchQuery = result.place.displayName ?: ""
                                                showSuggestions.value = false
                                                continuation.resume(Unit, onCancellation = {})
                                            }
                                            .addOnFailureListener { exception ->
                                                Log.e(
                                                    "Places",
                                                    "FetchPlace error: ${exception.message}",
                                                    exception
                                                )
                                                continuation.resume(Unit, onCancellation = {})
                                            }
                                    }
                                }
                            }
                        )
                        HorizontalDivider()
                    }
                }
            }
        }
    }
}