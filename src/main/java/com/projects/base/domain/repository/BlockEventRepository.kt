package com.projects.blreathe.data.repository

import com.projects.base.data.entity.ActionTaken
import com.projects.base.data.entity.BlockEventEntity
import com.projects.base.data.entity.SessionType
import kotlinx.coroutines.flow.Flow

interface BlockEventRepository {
    suspend fun logEvent(
        packageName: String,
        sessionType: SessionType,
        actionTaken: ActionTaken,
        focusSessionId: Long? = null
    )
    fun getGuardEventsToday(): Flow<List<BlockEventEntity>>
    fun getGuardEventsCountToday(): Flow<Int>
    fun getEventsForFocusSession(focusSessionId: Long): Flow<List<BlockEventEntity>>
    suspend fun getGuardEventsLast7Days(): List<BlockEventEntity>
    /** Number of successful breath blocks for the given app today */
    fun getBreathingCountToday(appPackage: String): Flow<Int>

    /** Number of skipped breaths for the given app today */
    fun getSkipCountToday(appPackage: String): Flow<Int>
}
