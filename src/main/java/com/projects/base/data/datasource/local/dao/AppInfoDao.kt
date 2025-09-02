package com.projects.base.data.datasource.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.projects.base.data.entity.AppInfoEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface AppInfoDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAppInfo(appInfo: AppInfoEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAppInfoList(appInfoList: List<AppInfoEntity>)

    @Update
    suspend fun updateAll(apps: List<AppInfoEntity>)

    @Query("SELECT * FROM app_info WHERE packageName = :packageName")
    suspend fun getAppInfo(packageName: String): AppInfoEntity?

    @Query("SELECT * FROM app_info")
    fun getAllAppInfo(): Flow<List<AppInfoEntity>>

    @Query("SELECT * FROM app_info")
    fun getRawAllAppInfo(): List<AppInfoEntity>

    @Query("SELECT * FROM app_info ORDER BY appName ASC")
    fun getAllAppInfoSortedByNameAsc(): Flow<List<AppInfoEntity>>

    @Query("SELECT * FROM app_info ORDER BY appName DESC")
    fun getAllAppInfoSortedByNameDesc(): Flow<List<AppInfoEntity>>

    @Query("SELECT * FROM app_info ORDER BY lastOpenTime DESC")
    fun getAllAppInfoSortedByLastOpenTime(): Flow<List<AppInfoEntity>>

//    @Query("SELECT * FROM app_info ORDER BY lastSearchTime DESC")
//    fun getAllAppInfoSortedByLastSearchTime(): Flow<List<AppInfoEntity>>


    @Query("UPDATE app_info SET lastOpenTime = :lastOpenTime WHERE packageName = :packageName")
    suspend fun updateLastOpenTime(packageName: String, lastOpenTime: Long)

    @Query("SELECT * FROM app_info WHERE isBlocked = 1 ORDER BY packageName ASC")
    fun getBlockedApps(): Flow<List<AppInfoEntity>>


}