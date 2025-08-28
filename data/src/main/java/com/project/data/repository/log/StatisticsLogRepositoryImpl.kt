package com.project.data.repository.log

import com.project.database.dao.StatisticsLogDAO
import com.project.database.entity.StatisticsLogEntity
import com.project.database.model.PeriodRoutineLog
import java.time.LocalDate
import javax.inject.Inject

class StatisticsLogRepositoryImpl @Inject constructor(
    private val statisticsLogDAO: StatisticsLogDAO
) : StatisticsLogRepository {
    override suspend fun getAll(): List<StatisticsLogEntity> {
        return statisticsLogDAO.getAll()
    }

    override suspend fun createStatisticsLog(entity: StatisticsLogEntity) {
        statisticsLogDAO.createStatisticsLog(entity)
    }

    override suspend fun getPeriodLog(start: LocalDate, end: LocalDate): List<PeriodRoutineLog> {
        return statisticsLogDAO.getPeriodLog(start, end)
    }
}
