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
    val content: String?,
    val start_date: String?,
    val deadline_date: String?,
    val color: Int?,
    @ColumnInfo(defaultValue = "false") val success: Boolean?
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
    tableName = "RoutineDetail",
    foreignKeys = [
        ForeignKey(
            entity = RoutineEntity::class,
            parentColumns = ["id"],
            childColumns = ["routine_id"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class RoutineDetailEntity(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id") val id: Int = 0,
    @ColumnInfo(name = "routine_id") var routineId: Int = -1,
    val number: Int,
    var title: String
)

@Entity(
    tableName = "RoutineLog"
)
data class RoutineLog(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val date: LocalDate,
    val routines: Map<Int, RoutineEntity>
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
    val routineLogId: Int,
    val percentage: Double
)
