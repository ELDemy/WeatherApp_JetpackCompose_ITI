package com.dmy.weather.presentation.search_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dmy.weather.data.model.CityModel
import com.dmy.weather.data.repo.CityRepository
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

private const val TAG = "SearchVM"

@FlowPreview
class SearchVM(val cityRepository: CityRepository) : ViewModel() {

    private val _uiState = MutableStateFlow(SearchUiState())
    val uiState = _uiState.asStateFlow()

    private val _searchQuery = MutableStateFlow("")
    val searchQuery = _searchQuery.asStateFlow()

    var selectedCity: CityModel? = null

    init {
        viewModelScope.launch {
            _searchQuery
                .debounce(300L)
                .distinctUntilChanged()
                .filter { it.isNotBlank() }
                .collectLatest { query -> search(query) }
        }
    }

    fun onQueryChanged(query: String) {
        _searchQuery.value = query
        if (query.isBlank()) {
            _uiState.value = SearchUiState()
        }
    }

    private suspend fun search(query: String) {
        _uiState.update { it.copy(isLoading = true, error = null) }
        cityRepository.getCitiesByName(query).fold(
            onSuccess = {
                _uiState.update { state ->
                    state.copy(
                        suggestions = it,
                        isLoading = false
                    )
                }
            },
            onFailure = {
                _uiState.update { state ->
                    state.copy(
                        error = it.message ?: "Can't get data", isLoading = false
                    )
                }
            }
        )
    }

    fun clearSearch() {
        _searchQuery.value = ""
        _uiState.value = SearchUiState()
    }
}

