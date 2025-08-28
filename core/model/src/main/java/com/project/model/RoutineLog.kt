package com.project.model

import java.time.LocalDate

data class RoutineLog(
    val id: Int = 0,
    val date: LocalDate?,
    val routines: Map<Int, Routine>?
)
