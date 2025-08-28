package com.project.data.repository.log

import com.project.database.entity.RoutineLogEntity
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

interface RoutineLogRepository {
    suspend fun createLog(entity: RoutineLogEntity)

    suspend fun getDateLog(date: LocalDate): RoutineLogEntity

    suspend fun update(entity: RoutineLogEntity)

    fun getTodayLog(): Flow<RoutineLogEntity?>

    suspend fun getWidgetTodayLog(date: LocalDate): RoutineLogEntity?
}
