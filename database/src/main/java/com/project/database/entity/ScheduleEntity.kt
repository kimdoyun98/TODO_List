package com.project.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.project.model.Schedule
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
)

fun ScheduleEntity.asExternalModel() = Schedule(
    id = id,
    title = title,
    start_date = start_date,
    end_date = end_date,
    color = color,
    success = success
)
