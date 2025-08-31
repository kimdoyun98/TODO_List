package com.project.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.project.database.entity.RoutineLogEntity
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

@Dao
interface RoutineLogDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun createLog(entity: RoutineLogEntity)

    @Update
    suspend fun update(entity: RoutineLogEntity)

    @Query("SELECT * FROM ROUTINELOG WHERE date = :date")
    suspend fun getDateLog(date: LocalDate): RoutineLogEntity

    @Query("SELECT * FROM ROUTINELOG ORDER BY id DESC LIMIT 1")
    fun getTodayLog(): Flow<RoutineLogEntity?>

    @Query("SELECT * FROM ROUTINELOG WHERE date = :date")
    fun getWidgetTodayLog(date: LocalDate): RoutineLogEntity?
}
