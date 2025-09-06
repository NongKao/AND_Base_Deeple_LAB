package com.projects.base.data.datasource.local.database

import androidx.room.TypeConverter
import com.projects.base.data.entity.ActionTaken
import com.projects.base.data.entity.SessionType

internal class Converters {
    @TypeConverter
    fun fromSessionType(value: SessionType): String = value.name

    @TypeConverter
    fun toSessionType(value: String): SessionType = SessionType.valueOf(value)

    @TypeConverter
    fun fromActionTaken(value: ActionTaken): String = value.name

    @TypeConverter
    fun toActionTaken(value: String): ActionTaken = ActionTaken.valueOf(value)
}
