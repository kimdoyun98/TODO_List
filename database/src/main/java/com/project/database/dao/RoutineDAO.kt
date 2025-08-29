package com.project.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.project.database.entity.RoutineEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface RoutineDAO {
    @Query("SELECT * FROM routineEntity")
    fun getRoutines(): Flow<List<RoutineEntity>>

    @Query("SELECT id FROM routineEntity WHERE title =:title")
    suspend fun getId(title: String): Int

    @Insert
    suspend fun insert(routineEntity: RoutineEntity): Long

    @Query("UPDATE RoutineEntity SET success =:suc")
    suspend fun update(suc: Boolean = false)

    @Query("UPDATE RoutineEntity SET success =:suc WHERE id =:id")
    suspend fun todaySuccess(id: Int, suc: Boolean = true)

    @Query("Delete From RoutineEntity WHERE id = :id")
    suspend fun delete(id: Int): Int

    @Query("UPDATE RoutineEntity SET success = 0 WHERE success = 1")
    suspend fun resetSuccess(): Int
}
