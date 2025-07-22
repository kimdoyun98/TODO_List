package com.example.todo_list.data.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.todo_list.data.room.StatisticsLog

@Dao
interface StatisticsLogDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun createStatisticsLog(entity: StatisticsLog)

    @Query("SELECT * FROM STATISTICSLOG")
    suspend fun getAll(): List<StatisticsLog>
}
