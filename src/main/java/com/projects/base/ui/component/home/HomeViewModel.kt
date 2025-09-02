package com.projects.base.ui.component.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.projects.base.data.Resource
import com.projects.base.data.model.Category
import com.projects.base.domain.repository.WallpaperRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed class HomeIntent {
    object LoadCategories : HomeIntent()
}

data class HomeUiState(
    val categories: List<Category> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)

class HomeViewModel(
    private val repository: WallpaperRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    fun handleIntent(intent: HomeIntent) {
        when (intent) {
            HomeIntent.LoadCategories -> loadCategories()
        }
    }

    private fun loadCategories() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, error = null)
            repository.getCategories().collect { res ->
                when (res) {
                    is Resource.Loading -> _uiState.value = _uiState.value.copy(isLoading = true)
                    is Resource.Success -> _uiState.value = HomeUiState(categories = res.data ?: emptyList())
                    is Resource.Error -> _uiState.value = HomeUiState(error = res.message)
                }
            }
        }
    }
}