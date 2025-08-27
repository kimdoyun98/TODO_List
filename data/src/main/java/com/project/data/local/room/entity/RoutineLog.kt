package com.project.data.local.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate

@Entity(
    tableName = "RoutineLog"
)
data class RoutineLog(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val date: LocalDate?,
    val routines: Map<Int, RoutineEntity>?
)
