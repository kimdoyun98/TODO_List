package com.project.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.project.model.RoutineLog
import java.time.LocalDate

@Entity(
    tableName = "RoutineLog"
)
data class RoutineLogEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val date: LocalDate?,
    val routines: Map<Int, RoutineEntity>?
)

fun RoutineLogEntity.asExternalModel() = RoutineLog(
    id = id,
    date = date,
    routines = routines?.map { it.key to it.value.asExternalModel() }?.toMap()
)
