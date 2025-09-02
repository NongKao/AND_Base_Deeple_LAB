package com.projects.base.domain.repository

import com.projects.base.data.model.Category
import com.projects.base.data.model.CategoryType
import com.projects.base.data.model.WallpaperItem
import com.projects.base.data.Resource
import kotlinx.coroutines.flow.Flow

interface WallpaperRepository {
    suspend fun getCategories(): Flow<Resource<List<Category>>>
    suspend fun getWallpapersByCategory(categoryType: CategoryType, page: Int): Flow<Resource<List<WallpaperItem>>>
}