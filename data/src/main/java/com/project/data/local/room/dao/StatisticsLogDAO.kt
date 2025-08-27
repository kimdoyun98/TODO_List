package com.project.data.local.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.project.data.local.room.entity.StatisticsLog
import com.project.data.repository.model.PeriodRoutineLog
import java.time.LocalDate

@Dao
interface StatisticsLogDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun createStatisticsLog(entity: StatisticsLog)

    @Query("SELECT * FROM STATISTICSLOG")
    suspend fun getAll(): List<StatisticsLog>

    @Query(
        """
        SELECT
            RoutineLog.date As date,
            RoutineLog.routines As routines,
            StatisticsLog.total As total,
            StatisticsLog.success As success
        FROM RoutineLog
        JOIN StatisticsLog ON RoutineLog.id = StatisticsLog.routineLogId
        WHERE RoutineLog.date BETWEEN :start AND :end
    """
    )
    suspend fun getPeriodLog(start: LocalDate, end: LocalDate): List<PeriodRoutineLog>
}
