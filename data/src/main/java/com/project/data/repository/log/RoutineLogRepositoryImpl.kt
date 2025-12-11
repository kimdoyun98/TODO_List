package com.project.data.repository.log

import com.project.data.model.asEntity
import com.project.database.dao.RoutineLogDAO
import com.project.database.entity.asExternalModel
import com.project.model.RoutineLog
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.time.LocalDate
import javax.inject.Inject

internal class RoutineLogRepositoryImpl @Inject constructor(
    private val routineLogDAO: RoutineLogDAO
) : RoutineLogRepository {
    override suspend fun createLog(entity: RoutineLog) {
        routineLogDAO.createLog(entity.asEntity())
    }

    override suspend fun getDateLog(date: LocalDate): RoutineLog =
        routineLogDAO.getDateLog(date).asExternalModel()

    override suspend fun update(entity: RoutineLog) {
        routineLogDAO.update(entity.asEntity())
    }

    override fun getTodayLog(): Flow<RoutineLog?> =
        routineLogDAO.getTodayLog()
            .map { it?.asExternalModel() }

    override suspend fun getWidgetTodayLog(date: LocalDate): RoutineLog? {
        return routineLogDAO.getWidgetTodayLog(date)?.asExternalModel()
    }
}
