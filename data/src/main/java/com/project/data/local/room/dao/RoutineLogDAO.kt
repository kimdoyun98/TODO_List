package com.project.data.local.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.project.data.local.room.entity.RoutineLog
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

@Dao
interface RoutineLogDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun createLog(entity: RoutineLog)

    @Update
    suspend fun update(entity: RoutineLog)

    @Query("SELECT * FROM ROUTINELOG WHERE date = :date")
    suspend fun getDateLog(date: LocalDate): RoutineLog

    @Query("SELECT * FROM ROUTINELOG ORDER BY id DESC LIMIT 1")
    fun getTodayLog(): Flow<RoutineLog?>

    @Query("SELECT * FROM ROUTINELOG WHERE date = :date")
    fun getWidgetTodayLog(date: LocalDate): RoutineLog?
}
