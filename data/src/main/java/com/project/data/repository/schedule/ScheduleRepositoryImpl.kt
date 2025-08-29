package com.project.data.repository.schedule

import android.util.Log
import com.project.data.model.asEntity
import com.project.database.dao.ScheduleDAO
import com.project.database.entity.asExternalModel
import com.project.model.Schedule
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ScheduleRepositoryImpl @Inject constructor(
    private val scheduleDAO: ScheduleDAO
) : ScheduleRepository {
    override fun selectAll(): Flow<List<Schedule>> =
        scheduleDAO.getAll()
            .map {
                it.map { entity ->
                    entity.asExternalModel()
                }
            }

    override fun getRecentSchedule() =
        scheduleDAO.getRecentSchedule()
            .filterNotNull()
            .map {
                it.asExternalModel()
            }

    override suspend fun getWidgetRecentSchedule() =
        scheduleDAO.getWidgetRecentSchedule()?.asExternalModel()

    override suspend fun delete(id: Int) = withContext(Dispatchers.IO) { scheduleDAO.delete(id) }

    override suspend fun insert(toDoEntity: Schedule) =
        withContext(Dispatchers.IO) { scheduleDAO.insert(toDoEntity.asEntity()) }

    override suspend fun update(toDoEntity: Schedule) =
        withContext(Dispatchers.IO) { scheduleDAO.update(toDoEntity.asEntity()) }

    override suspend fun success(id: Int) = withContext(Dispatchers.IO) { scheduleDAO.success(id) }

}
