package com.project.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class RoutineEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String?,
    val day: List<Boolean>?,
    var success: Boolean? = null,
    var time: String
)
