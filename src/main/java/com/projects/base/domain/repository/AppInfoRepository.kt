package com.projects.blreathe.data.repository

import com.projects.base.data.entity.AppInfoEntity
import com.projects.base.data.entity.FilterType
import com.projects.base.domain.repository.BaseRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface AppInfoRepository: BaseRepository {
    fun getAllAppInfo(): Flow<List<AppInfoEntity>>
    suspend fun updateAppInfo(appInfoEntity: AppInfoEntity)
    suspend fun updateAppInfoList(apps: List<AppInfoEntity>)
    fun setFilterType(filterType: FilterType)
    fun getBlockedAppsFlow(): StateFlow<List<AppInfoEntity>>
    fun getBlockedApps(): List<AppInfoEntity>
    
    // Methods for temporarily allowed apps
    fun allowAppTemporarily(packageName: String)
    fun isAppTemporarilyAllowed(packageName: String): Boolean
    fun getTemporarilyAllowedApps(): Flow<Set<String>>
    fun clearTemporarilyAllowedApps()
    
    // Method to remove app from blocked list
    suspend fun removeAppFromBlockedList(packageName: String)
}