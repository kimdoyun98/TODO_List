package com.project.model

import java.time.LocalDate

data class PeriodRoutineLog(
    val date: LocalDate? = null,
    val routines: Map<Int, Routine>? = emptyMap(),
    val total: Int = 0,
    val success: Int = 0,
)
