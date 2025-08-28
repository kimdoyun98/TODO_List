package com.project.data.repository.schedule

import com.project.database.entity.ScheduleEntity
import kotlinx.coroutines.flow.Flow

interface ScheduleRepository {
    fun selectAll(): Flow<List<ScheduleEntity>>
    fun getRecentSchedule(): Flow<ScheduleEntity>
    suspend fun getWidgetRecentSchedule(): ScheduleEntity?
    suspend fun insert(toDoEntity: ScheduleEntity)
    suspend fun delete(id: Int): Int
    suspend fun update(toDoEntity: ScheduleEntity)
    suspend fun success(id: Int)
}
