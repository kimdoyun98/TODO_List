package com.project.data.repository.log

import com.project.database.dao.RoutineLogDAO
import com.project.database.entity.RoutineLogEntity
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate
import javax.inject.Inject

class RoutineLogRepositoryImpl @Inject constructor(
    private val routineLogDAO: RoutineLogDAO
) : RoutineLogRepository {
    override suspend fun createLog(entity: RoutineLogEntity) {
        routineLogDAO.createLog(entity)
    }

    override suspend fun getDateLog(date: LocalDate): RoutineLogEntity {
        return routineLogDAO.getDateLog(date)
    }

    override suspend fun update(entity: RoutineLogEntity) {
        routineLogDAO.update(entity)
    }

    override fun getTodayLog(): Flow<RoutineLogEntity?> {
        return routineLogDAO.getTodayLog()
    }

    override suspend fun getWidgetTodayLog(date: LocalDate): RoutineLogEntity? {
        return routineLogDAO.getWidgetTodayLog(date)
    }
}
