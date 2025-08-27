package com.project.data.repository.log

import com.project.data.local.room.entity.RoutineLog
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

interface RoutineLogRepository {
    suspend fun createLog(entity: RoutineLog)

    suspend fun getDateLog(date: LocalDate): RoutineLog

    suspend fun update(entity: RoutineLog)

    fun getTodayLog(): Flow<RoutineLog?>

    suspend fun getWidgetTodayLog(date: LocalDate): RoutineLog?
}
