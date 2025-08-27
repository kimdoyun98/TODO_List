package com.project.data.repository.model

import com.project.data.local.room.entity.RoutineEntity
import java.time.LocalDate

data class PeriodRoutineLog(
    val date: LocalDate? = null,
    val routines: Map<Int, RoutineEntity>? = emptyMap(),
    val total: Int = 0,
    val success: Int = 0,
)
