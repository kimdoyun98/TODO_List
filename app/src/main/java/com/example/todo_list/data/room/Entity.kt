package com.example.todo_list.data.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.io.Serializable
import java.time.LocalDate

@Entity
data class ScheduleEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String?,
    val start_date: LocalDate?,
    val end_date: LocalDate?,
    val color: Int?,
    val success: Boolean? = false
) : Serializable

@Entity
data class RoutineEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String?,
    val day: List<Boolean>?,
    var success: Boolean? = null,
    var time: String
)

@Entity(
    tableName = "RoutineLog"
)
data class RoutineLog(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val date: LocalDate?,
    val routines: Map<Int, RoutineEntity>?
)

@Entity(
    tableName = "StatisticsLog",
    foreignKeys = [
        ForeignKey(
            entity = RoutineLog::class,
            parentColumns = ["id"],
            childColumns = ["routineLogId"],
            onDelete = ForeignKey.NO_ACTION
        )
    ]
)
data class StatisticsLog(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val routineLogId: Int?,
    val total: Int,
    val success: Int,
)
