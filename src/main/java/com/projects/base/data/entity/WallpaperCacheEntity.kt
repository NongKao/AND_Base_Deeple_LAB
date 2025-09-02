package com.projects.base.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "wallpapers")
data class WallpaperCacheEntity(
    @PrimaryKey val id: String,
    val categoryType: String,
    val page: Int,
    val author: String,
    val url: String,
    val downloadUrl: String,
    val width: Int,
    val height: Int,
    val updatedAt: Long
)
