package com.project.data.repository.log

import com.project.database.dao.StatisticsLogDAO
import com.project.database.entity.StatisticsLog
import com.project.database.model.PeriodRoutineLog
import java.time.LocalDate
import javax.inject.Inject

class StatisticsLogRepositoryImpl @Inject constructor(
    private val statisticsLogDAO: StatisticsLogDAO
) : StatisticsLogRepository {
    override suspend fun getAll(): List<StatisticsLog> {
        return statisticsLogDAO.getAll()
    }

    override suspend fun createStatisticsLog(entity: StatisticsLog) {
        statisticsLogDAO.createStatisticsLog(entity)
    }

    override suspend fun getPeriodLog(start: LocalDate, end: LocalDate): List<PeriodRoutineLog> {
        return statisticsLogDAO.getPeriodLog(start, end)
    }
}
