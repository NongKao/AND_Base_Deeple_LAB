package com.projects.base.ui.component.splash

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

class SplashViewModel(
    private val repository: WallpaperRepository
) : ViewModel() {

    private val _wallpapers = MutableStateFlow<List<WallpaperItem>>(emptyList())
    val wallpapers: StateFlow<List<WallpaperItem>> = _wallpapers.asStateFlow()

    fun preload() {
        viewModelScope.launch {
            repository.getWallpapersByCategory(CategoryType.AI_ART, page = 1).collect { res ->
                if (res is Resource.Success) {
                    _wallpapers.value = res.data.orEmpty()
                }
            }
        }
    }
}