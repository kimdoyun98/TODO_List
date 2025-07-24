package com.example.todo_list.data.repository.log

import com.example.todo_list.data.room.PeriodRoutineLog
import com.example.todo_list.data.room.StatisticsLog
import java.time.LocalDate

interface StatisticsLogRepository {
    suspend fun getAll(): List<StatisticsLog>

    suspend fun createStatisticsLog(entity: StatisticsLog)

    suspend fun getPeriodLog(start: LocalDate, end: LocalDate): List<PeriodRoutineLog>
}
