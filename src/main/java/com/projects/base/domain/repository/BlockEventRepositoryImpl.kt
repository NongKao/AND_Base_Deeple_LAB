package com.projects.blreathe.data.repository

import com.projects.base.data.entity.ActionTaken
import com.projects.base.data.entity.BlockEventEntity
import com.projects.base.data.entity.SessionType
import com.projects.base.data.datasource.local.dao.BlockEventDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import java.util.Calendar

class BlockEventRepositoryImpl(
    private val blockEventDao: BlockEventDao
) : BlockEventRepository {

    override suspend fun logEvent(
        packageName: String,
        sessionType: SessionType,
        actionTaken: ActionTaken,
        focusSessionId: Long?
    ) = withContext(Dispatchers.IO) {
        val event = BlockEventEntity(
            timestamp = System.currentTimeMillis(),
            packageName = packageName,
            sessionType = sessionType,
            focusSessionId = focusSessionId,
            actionTaken = actionTaken
        )
        blockEventDao.insert(event)
    }

    override fun getGuardEventsToday(): Flow<List<BlockEventEntity>> {
        return blockEventDao.getEventsSince(SessionType.GUARD, getStartOfToday())
    }

    override fun getGuardEventsCountToday(): Flow<Int> {
        return blockEventDao.getEventsCountSince(SessionType.GUARD, getStartOfToday())
    }
    
    override fun getEventsForFocusSession(focusSessionId: Long): Flow<List<BlockEventEntity>> {
        return blockEventDao.getEventsForFocusSession(focusSessionId)
    }

    override suspend fun getGuardEventsLast7Days(): List<BlockEventEntity> = withContext(Dispatchers.IO) {
        val cal = Calendar.getInstance()
        val endTime = cal.timeInMillis
        cal.add(Calendar.DAY_OF_YEAR, -7)
        val startTime = getStartOfDay(cal)
        blockEventDao.getEventsInRange(SessionType.GUARD, startTime, endTime)
    }

    override fun getBreathingCountToday(appPackage: String): Flow<Int> {
        return blockEventDao.getActionCountSince(
            SessionType.GUARD,
            ActionTaken.BLOCKED,
            appPackage,
            getStartOfToday()
        )
    }

    override fun getSkipCountToday(appPackage: String): Flow<Int> {
        return blockEventDao.getActionCountSince(
            SessionType.GUARD,
            ActionTaken.ALLOWED_TEMPORARILY,
            appPackage,
            getStartOfToday()
        )
    }

    private fun getStartOfToday(): Long {
        return getStartOfDay(Calendar.getInstance())
    }

    private fun getStartOfDay(calendar: Calendar): Long {
        return calendar.apply {
            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }.timeInMillis
    }
}
