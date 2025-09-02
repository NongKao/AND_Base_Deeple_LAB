package com.projects.base.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "block_events",
    foreignKeys = [
        ForeignKey(
            entity = FocusSessionEntity::class,
            parentColumns = ["id"],
            childColumns = ["focus_session_id"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class BlockEventEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val timestamp: Long,
    @ColumnInfo(name = "package_name")
    val packageName: String,
    @ColumnInfo(name = "session_type")
    val sessionType: SessionType,
    @ColumnInfo(name = "focus_session_id", index = true)
    val focusSessionId: Long?,
    @ColumnInfo(name = "action_taken")
    val actionTaken: ActionTaken
)

enum class SessionType {
    GUARD,
    FOCUS
}

enum class ActionTaken {
    BLOCKED,
    ALLOWED_TEMPORARILY
}
