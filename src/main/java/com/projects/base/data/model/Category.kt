package com.projects.base.data.model

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