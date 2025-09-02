package com.projects.base.data.entity

data class Category(
    val id: String,
    val name: String,
    val type: CategoryType
)

enum class CategoryType {
    AI_ART,
    HOUR_24,
    VIDEO
}

data class WallpaperItem(
    val id: String,
    val url: String,
    val downloadUrl: String,
    val author: String,
    val width: Int,
    val height: Int
)

// Picsum API Response
data class PicsumImage(
    val id: String,
    val author: String,
    val width: Int,
    val height: Int,
    val url: String,
    val download_url: String
)