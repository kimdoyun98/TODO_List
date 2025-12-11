package com.project.data.repository.log

import com.project.data.model.asEntity
import com.project.database.dao.StatisticsLogDAO
import com.project.database.entity.asExternalModel
import com.project.model.PeriodRoutineLog
import com.project.model.StatisticsLog
import java.time.LocalDate
import javax.inject.Inject

internal class StatisticsLogRepositoryImpl @Inject constructor(
    private val statisticsLogDAO: StatisticsLogDAO
) : StatisticsLogRepository {
    override suspend fun getAll(): List<StatisticsLog> {
        return statisticsLogDAO.getAll().map { it.asExternalModel() }
    }

    override suspend fun createStatisticsLog(entity: StatisticsLog) {
        statisticsLogDAO.createStatisticsLog(entity.asEntity())
    }

    override suspend fun getPeriodLog(start: LocalDate, end: LocalDate): List<PeriodRoutineLog> {
        return statisticsLogDAO.getPeriodLog(start, end)
    }
}
