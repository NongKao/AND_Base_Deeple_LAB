package com.projects.base.domain.wallpaper

interface WallpaperSetter {
    suspend fun setHome(url: String)
    suspend fun setLock(url: String)
    suspend fun setBoth(url: String)
}
