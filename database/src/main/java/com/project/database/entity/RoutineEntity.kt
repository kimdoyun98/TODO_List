package com.project.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.project.model.Routine

@Entity
data class RoutineEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String?,
    val day: List<Boolean>?,
    var success: Boolean? = null,
    var time: String
)

fun RoutineEntity.asExternalModel() = Routine(
    id = id,
    title = title ?: "",
    day = day ?: listOf(false, false, false, false, false, false, false),
    success = success,
    time = time
)
