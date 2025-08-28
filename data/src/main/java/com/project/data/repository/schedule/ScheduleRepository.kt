package com.project.data.repository.schedule

import com.project.model.Schedule
import kotlinx.coroutines.flow.Flow

interface ScheduleRepository {
    fun selectAll(): Flow<List<Schedule>>
    fun getRecentSchedule(): Flow<Schedule>
    suspend fun getWidgetRecentSchedule(): Schedule?
    suspend fun insert(toDoEntity: Schedule)
    suspend fun delete(id: Int): Int
    suspend fun update(toDoEntity: Schedule)
    suspend fun success(id: Int)
}
