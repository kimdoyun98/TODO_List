package com.project.data.local.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.project.data.local.room.entity.ScheduleEntity
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
