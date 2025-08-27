package com.example.todo_list.data.repository.log

import com.example.todo_list.data.room.RoutineLog
import com.project.data.local.room.dao.RoutineLogDAO
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate
import javax.inject.Inject

class RoutineLogRepositoryImpl @Inject constructor(
    private val routineLogDAO: RoutineLogDAO
) : RoutineLogRepository {
    override suspend fun createLog(entity: RoutineLog) {
        routineLogDAO.createLog(entity)
    }

    override suspend fun getDateLog(date: LocalDate): RoutineLog {
        return routineLogDAO.getDateLog(date)
    }

    override suspend fun update(entity: RoutineLog) {
        routineLogDAO.update(entity)
    }

    override fun getTodayLog(): Flow<RoutineLog?> {
        return routineLogDAO.getTodayLog()
    }

    override suspend fun getWidgetTodayLog(date: LocalDate): RoutineLog? {
        return routineLogDAO.getWidgetTodayLog(date)
    }
}
