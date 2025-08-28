package com.project.data.repository.routine

import com.project.database.dao.RoutineDAO
import com.project.database.entity.asExternalModel
import com.project.model.Routine
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class RoutineRepositoryImpl @Inject constructor(
    private val routineDAO: RoutineDAO
) : RoutineRepository {
    override fun selectAll(): Flow<List<Routine>> =
        routineDAO.getRoutines()
            .map {
                it.map { entity ->
                    entity.asExternalModel()
                }
            }

    override suspend fun getId(title: String): Int = routineDAO.getId(title)

    override suspend fun update() = routineDAO.update()

    override suspend fun todaySuccess(id: Int) = routineDAO.todaySuccess(id)

    override suspend fun insert(routineEntity: Routine): Long =
        routineDAO.insert(routineEntity)

    override suspend fun delete(id: Int) = routineDAO.delete(id)

    override suspend fun resetSuccess(): Int = routineDAO.resetSuccess()

}
