package com.project.data.repository.routine

import com.project.database.dao.RoutineDAO
import com.project.database.entity.RoutineEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RoutineRepositoryImpl @Inject constructor(
    private val routineDAO: RoutineDAO
) : RoutineRepository {
    override fun selectAll(): Flow<List<RoutineEntity>> = routineDAO.getRoutines()

    override suspend fun getId(title: String): Int = routineDAO.getId(title)

    override suspend fun update() = routineDAO.update()

    override suspend fun todaySuccess(id: Int) = routineDAO.todaySuccess(id)

    override suspend fun insert(routineEntity: RoutineEntity): Long =
        routineDAO.insert(routineEntity)

    override suspend fun delete(id: Int) = routineDAO.delete(id)

    override suspend fun resetSuccess(): Int = routineDAO.resetSuccess()

}
