package com.example.todo_list.data.repository.schedule

import com.example.todo_list.data.room.ScheduleEntity
import kotlinx.coroutines.flow.Flow

interface ScheduleRepository {
    fun selectAll(): Flow<List<ScheduleEntity>>
    fun getRecentSchedule(): Flow<ScheduleEntity>
    suspend fun insert(toDoEntity: ScheduleEntity)
    suspend fun delete(id: Int): Int
    suspend fun update(toDoEntity: ScheduleEntity)
    suspend fun success(id: Int)
}
