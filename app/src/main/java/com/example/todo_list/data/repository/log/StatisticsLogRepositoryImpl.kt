package com.example.todo_list.data.repository.log

import com.example.todo_list.data.room.StatisticsLog
import com.example.todo_list.data.room.dao.StatisticsLogDAO
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
}
