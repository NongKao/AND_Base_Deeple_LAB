package com.projects.base.data.datasource.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.projects.base.data.entity.WallpaperCacheEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface WallpaperDao {
    @Query("SELECT * FROM wallpapers WHERE categoryType = :category ORDER BY page, id")
    fun observeByCategory(category: String): Flow<List<WallpaperCacheEntity>>

    @Query("SELECT * FROM wallpapers WHERE categoryType = :category AND page = :page")
    suspend fun getByCategoryPage(category: String, page: Int): List<WallpaperCacheEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertAll(items: List<WallpaperCacheEntity>)

    @Query("DELETE FROM wallpapers WHERE categoryType = :category")
    suspend fun deleteByCategory(category: String)

    @Query("DELETE FROM wallpapers WHERE updatedAt < :thresholdMillis")
    suspend fun prune(thresholdMillis: Long)

    @Query("SELECT MIN(updatedAt) FROM wallpapers WHERE categoryType = :category AND page = :page")
    suspend fun oldestUpdatedAt(category: String, page: Int): Long?
}
