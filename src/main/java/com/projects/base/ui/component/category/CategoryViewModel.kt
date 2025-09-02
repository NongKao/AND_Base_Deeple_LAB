package com.projects.base.ui.component.category

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.projects.base.data.Resource
import com.projects.base.data.model.CategoryType
import com.projects.base.data.model.WallpaperItem
import com.projects.base.domain.repository.WallpaperRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed class CategoryIntent {
    data class LoadImages(val categoryType: CategoryType, val page: Int = 1) : CategoryIntent()
}

data class CategoryUiState(
    val images: List<WallpaperItem> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)

class CategoryViewModel(
    private val repository: WallpaperRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(CategoryUiState())
    val uiState: StateFlow<CategoryUiState> = _uiState.asStateFlow()

    fun handleIntent(intent: CategoryIntent) {
        when (intent) {
            is CategoryIntent.LoadImages -> loadImages(intent.categoryType, intent.page)
        }
    }

    private fun loadImages(categoryType: CategoryType, page: Int) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, error = null)
            repository.getWallpapersByCategory(categoryType, page).collect { res ->
                when (res) {
                    is Resource.Loading -> _uiState.value = _uiState.value.copy(isLoading = true)
                    is Resource.Success -> _uiState.value = CategoryUiState(images = res.data ?: emptyList())
                    is Resource.Error -> _uiState.value = CategoryUiState(error = res.message)
                }
            }
        }
    }
}