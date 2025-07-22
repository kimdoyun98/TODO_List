package com.example.todo_list.data.repository.log

import com.example.todo_list.data.room.StatisticsLog

interface StatisticsLogRepository {
    suspend fun getAll(): List<StatisticsLog>

    suspend fun createStatisticsLog(entity: StatisticsLog)
}
