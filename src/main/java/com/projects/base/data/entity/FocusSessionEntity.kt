package com.projects.base.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "focus_sessions")
data class FocusSessionEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val intention: String,
    val startTime: Long,
    var endTime: Long?,
    var durationMillis: Long?,
    var isCompleted: Boolean = false
)
