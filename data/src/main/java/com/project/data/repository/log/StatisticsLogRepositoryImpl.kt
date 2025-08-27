package com.example.todo_list.data.repository.log

import com.example.todo_list.data.room.PeriodRoutineLog
import com.example.todo_list.data.room.StatisticsLog
import com.project.data.local.room.dao.StatisticsLogDAO
import java.time.LocalDate
import javax.inject.Inject

class StatisticsLogRepositoryImpl @Inject constructor(
    private val statisticsLogDAO: StatisticsLogDAO
): StatisticsLogRepository {
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
