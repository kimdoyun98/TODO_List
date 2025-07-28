package com.example.todo_list.data.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.todo_list.data.room.RoutineDetailEntity
import com.example.todo_list.data.room.RoutineEntity
import com.example.todo_list.data.room.ScheduleEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ScheduleDAO {
    @Query("SELECT * FROM scheduleEntity WHERE success = :suc")
    fun getAll(suc: Boolean = false): Flow<List<ScheduleEntity>>

    @Query("SELECT * FROM scheduleentity WHERE success = 0 ORDER BY start_date LIMIT 1")
    fun getRecentSchedule(): Flow<ScheduleEntity>

    @Query("SELECT * FROM scheduleentity WHERE success = 0 ORDER BY start_date LIMIT 1")
    fun getWidgetRecentSchedule(): ScheduleEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(toDoEntity: ScheduleEntity)

    @Query("Delete From scheduleEntity WHERE id = :id")
    suspend fun delete(id: Int): Int

    @Update
    suspend fun update(toDoEntity: ScheduleEntity)

    @Query("Update scheduleEntity SET success = :suc WHERE id = :id")
    suspend fun success(id: Int, suc: Boolean = true)
}

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

    @Query("SELECT * FROM RoutineDetail WHERE routine_id =:id")
    fun getRoutineDetails(id: Int): Flow<List<RoutineDetailEntity>>

    @Insert
    suspend fun insertRoutineDetail(routineDetail: RoutineDetailEntity)

    @Update
    suspend fun updateRoutineDetail(routineDetail: RoutineDetailEntity): Int
}
