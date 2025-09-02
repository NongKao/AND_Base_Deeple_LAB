package com.projects.base.data.datasource.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.projects.base.data.entity.BlockEventEntity
import com.projects.base.data.entity.SessionType
import kotlinx.coroutines.flow.Flow
import com.projects.base.data.entity.ActionTaken

@Dao
interface BlockEventDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(event: BlockEventEntity)

    @Query("SELECT * FROM block_events WHERE session_type = :sessionType AND timestamp >= :startTime ORDER BY timestamp DESC")
    fun getEventsSince(sessionType: SessionType, startTime: Long): Flow<List<BlockEventEntity>>

    @Query("SELECT COUNT(id) FROM block_events WHERE session_type = :sessionType AND timestamp >= :startTime")
    fun getEventsCountSince(sessionType: SessionType, startTime: Long): Flow<Int>
    
    @Query("SELECT * FROM block_events WHERE focus_session_id = :focusSessionId ORDER BY timestamp DESC")
    fun getEventsForFocusSession(focusSessionId: Long): Flow<List<BlockEventEntity>>

    @Query("SELECT * FROM block_events WHERE session_type = :sessionType AND timestamp BETWEEN :startTime AND :endTime")
    suspend fun getEventsInRange(sessionType: SessionType, startTime: Long, endTime: Long): List<BlockEventEntity>

    // Count events by action (blocked or allowed) for a specific app since a given time
    @Query("SELECT COUNT(id) FROM block_events WHERE session_type = :sessionType AND action_taken = :actionTaken AND package_name = :packageName AND timestamp >= :startTime")
    fun getActionCountSince(
        sessionType: SessionType,
        actionTaken: ActionTaken,
        packageName: String,
        startTime: Long
    ): Flow<Int>

    // Count events by action for a specific app between two timestamps
    @Query("SELECT COUNT(id) FROM block_events WHERE session_type = :sessionType AND action_taken = :actionTaken AND package_name = :packageName AND timestamp BETWEEN :startTime AND :endTime")
    suspend fun getActionCountInRange(
        sessionType: SessionType,
        actionTaken: ActionTaken,
        packageName: String,
        startTime: Long,
        endTime: Long
    ): Int
}
