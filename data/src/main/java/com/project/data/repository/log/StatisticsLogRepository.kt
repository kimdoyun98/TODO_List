package com.project.data.repository.log

import com.project.data.local.room.entity.StatisticsLog
import com.project.data.repository.model.PeriodRoutineLog
import java.time.LocalDate

interface StatisticsLogRepository {
    suspend fun getAll(): List<StatisticsLog>

    suspend fun createStatisticsLog(entity: StatisticsLog)

    suspend fun getPeriodLog(start: LocalDate, end: LocalDate): List<PeriodRoutineLog>
}
