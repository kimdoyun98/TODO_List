package com.example.todo_list.data.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.todo_list.data.room.PeriodRoutineLog
import com.example.todo_list.data.room.StatisticsLog
import java.time.LocalDate

@Dao
interface StatisticsLogDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun createStatisticsLog(entity: StatisticsLog)

    @Query("SELECT * FROM STATISTICSLOG")
    suspend fun getAll(): List<StatisticsLog>

    @Query("""
        SELECT
            RoutineLog.date As date,
            RoutineLog.routines As routines,
            StatisticsLog.total As total,
            StatisticsLog.success As success
        FROM RoutineLog
        JOIN StatisticsLog ON RoutineLog.id = StatisticsLog.routineLogId
        WHERE RoutineLog.date BETWEEN :start AND :end
    """)
    suspend fun getPeriodLog(start: LocalDate, end: LocalDate): List<PeriodRoutineLog>
}
