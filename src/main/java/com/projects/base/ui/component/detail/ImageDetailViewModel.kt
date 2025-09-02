package com.projects.base.ui.component.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.projects.base.domain.wallpaper.WallpaperSetter
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed class ImageDetailIntent {
    data class SetAsHome(val url: String) : ImageDetailIntent()
    data class SetAsLock(val url: String) : ImageDetailIntent()
    data class SetAsBoth(val url: String) : ImageDetailIntent()
}

data class ImageDetailUiState(
    val isSetting: Boolean = false,
    val message: String? = null,
    val error: String? = null
)

class ImageDetailViewModel(
    private val wallpaperSetter: WallpaperSetter
) : ViewModel() {

    private val _uiState = MutableStateFlow(ImageDetailUiState())
    val uiState: StateFlow<ImageDetailUiState> = _uiState.asStateFlow()

    fun handleIntent(intent: ImageDetailIntent) {
        when (intent) {
            is ImageDetailIntent.SetAsHome -> setHome(intent.url)
            is ImageDetailIntent.SetAsLock -> setLock(intent.url)
            is ImageDetailIntent.SetAsBoth -> setBoth(intent.url)
        }
    }

    private fun setHome(url: String) {
        viewModelScope.launch {
            _uiState.value = ImageDetailUiState(isSetting = true)
            runCatching { wallpaperSetter.setHome(url) }
                .onSuccess { _uiState.value = ImageDetailUiState(message = "SET_OK") }
                .onFailure { _uiState.value = ImageDetailUiState(error = it.message ?: "ERROR") }
        }
    }

    private fun setLock(url: String) {
        viewModelScope.launch {
            _uiState.value = ImageDetailUiState(isSetting = true)
            runCatching { wallpaperSetter.setLock(url) }
                .onSuccess { _uiState.value = ImageDetailUiState(message = "SET_OK") }
                .onFailure { _uiState.value = ImageDetailUiState(error = it.message ?: "ERROR_LOCK") }
        }
    }

    private fun setBoth(url: String) {
        viewModelScope.launch {
            _uiState.value = ImageDetailUiState(isSetting = true)
            runCatching { wallpaperSetter.setBoth(url) }
                .onSuccess { _uiState.value = ImageDetailUiState(message = "SET_OK") }
                .onFailure { _uiState.value = ImageDetailUiState(error = it.message ?: "ERROR") }
        }
    }
}
