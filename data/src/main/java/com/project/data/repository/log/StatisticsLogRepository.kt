package com.project.data.repository.log

import com.project.model.PeriodRoutineLog
import com.project.model.StatisticsLog
import java.time.LocalDate

interface StatisticsLogRepository {
    suspend fun getAll(): List<StatisticsLog>

    suspend fun createStatisticsLog(entity: StatisticsLog)

    suspend fun getPeriodLog(start: LocalDate, end: LocalDate): List<PeriodRoutineLog>
}
