package com.projects.base.domain.repository

import com.projects.base.data.entity.FocusSessionEntity
import com.projects.base.data.datasource.local.dao.FocusSessionDao
import com.projects.blreathe.data.repository.FocusSessionRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class FocusSessionRepositoryImpl(
    private val focusSessionDao: FocusSessionDao
) : FocusSessionRepository {

    override suspend fun startSession(intention: String): Long = withContext(Dispatchers.IO) {
        val session = FocusSessionEntity(
            intention = intention,
            startTime = System.currentTimeMillis(),
            endTime = null,
            durationMillis = null,
            isCompleted = false
        )
        focusSessionDao.insert(session)
    }

    override suspend fun endSession(sessionId: Long) {
        withContext(Dispatchers.IO) {
            val session = focusSessionDao.getSessionById(sessionId)
            session?.let {
                it.endTime = System.currentTimeMillis()
                it.durationMillis = it.endTime!! - it.startTime
                it.isCompleted = true
                focusSessionDao.update(it)
            }
        }
    }

    override fun getActiveSession(): Flow<FocusSessionEntity?> {
        return focusSessionDao.getActiveSession()
    }

    override fun getSessionHistory(): Flow<List<FocusSessionEntity>> {
        return focusSessionDao.getSessionHistory()
    }
}
