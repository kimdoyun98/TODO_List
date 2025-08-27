package com.example.todo_list.data.repository.schedule

import com.example.todo_list.data.room.dao.ScheduleDAO
import com.example.todo_list.data.room.ScheduleEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ScheduleRepositoryImpl @Inject constructor(
    private val scheduleDAO: ScheduleDAO
): ScheduleRepository {
    override fun selectAll(): Flow<List<ScheduleEntity>> = scheduleDAO.getAll()

    override fun getRecentSchedule(): Flow<ScheduleEntity> = scheduleDAO.getRecentSchedule()

    override suspend fun getWidgetRecentSchedule(): ScheduleEntity? = scheduleDAO.getWidgetRecentSchedule()

    override suspend fun delete(id : Int) = withContext(Dispatchers.IO) { scheduleDAO.delete(id) }

    override suspend fun insert (toDoEntity: ScheduleEntity) = withContext(Dispatchers.IO) { scheduleDAO.insert(toDoEntity) }

    override suspend fun update (toDoEntity: ScheduleEntity) = withContext(Dispatchers.IO){ scheduleDAO.update(toDoEntity) }

    override suspend fun success(id: Int) = withContext(Dispatchers.IO){ scheduleDAO.success(id) }

}
