package com.example.todo_list.data.repository.routine

import com.example.todo_list.data.room.RoutineDetailEntity
import com.example.todo_list.data.room.RoutineEntity
import kotlinx.coroutines.flow.Flow

interface RoutineRepository {
    fun selectAll(): Flow<List<RoutineEntity>>
    fun getRoutineDetail(id: Int): Flow<List<RoutineDetailEntity>>
    suspend fun getId(title: String): Int
    suspend fun update()
    suspend fun todaySuccess(id: Int)
    suspend fun insert(routineEntity: RoutineEntity): Long
    suspend fun delete(id: Int): Int
    suspend fun resetSuccess(): Int
    suspend fun insertRoutineDetail(routineDetail: RoutineDetailEntity)
}
