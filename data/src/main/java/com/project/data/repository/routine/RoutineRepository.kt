package com.project.data.repository.routine

import com.project.database.entity.RoutineEntity
import kotlinx.coroutines.flow.Flow

interface RoutineRepository {
    fun selectAll(): Flow<List<RoutineEntity>>
    suspend fun getId(title: String): Int
    suspend fun update()
    suspend fun todaySuccess(id: Int)
    suspend fun insert(routineEntity: RoutineEntity): Long
    suspend fun delete(id: Int): Int
    suspend fun resetSuccess(): Int
}
