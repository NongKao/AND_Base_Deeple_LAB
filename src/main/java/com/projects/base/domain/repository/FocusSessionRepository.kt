package com.projects.blreathe.data.repository

import com.projects.base.data.entity.FocusSessionEntity
import kotlinx.coroutines.flow.Flow

interface FocusSessionRepository {
    suspend fun startSession(intention: String): Long
    suspend fun endSession(sessionId: Long)
    fun getActiveSession(): Flow<FocusSessionEntity?>
    fun getSessionHistory(): Flow<List<FocusSessionEntity>>
}
