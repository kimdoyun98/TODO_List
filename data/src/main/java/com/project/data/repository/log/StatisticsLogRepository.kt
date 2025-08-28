package com.project.data.repository.log

import com.project.database.entity.StatisticsLogEntity
import com.project.database.model.PeriodRoutineLog
import java.time.LocalDate

interface StatisticsLogRepository {
    suspend fun getAll(): List<StatisticsLogEntity>

    suspend fun createStatisticsLog(entity: StatisticsLogEntity)

    suspend fun getPeriodLog(start: LocalDate, end: LocalDate): List<PeriodRoutineLog>
}
