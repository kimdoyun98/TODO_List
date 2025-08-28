package com.project.database.model

import com.project.database.entity.RoutineEntity
import java.time.LocalDate

data class PeriodRoutineLog(
    val date: LocalDate? = null,
    val routines: Map<Int, RoutineEntity>? = emptyMap(),
    val total: Int = 0,
    val success: Int = 0,
)
