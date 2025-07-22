package com.example.todo_list.data.repository.log

import com.example.todo_list.data.room.RoutineLog
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

interface RoutineLogRepository {
    suspend fun createLog(entity: RoutineLog)

    suspend fun getDateLog(date: LocalDate): RoutineLog

    suspend fun update(entity: RoutineLog)

    fun getTodayLog(): Flow<RoutineLog?>
}
