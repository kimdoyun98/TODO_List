package com.project.model

import java.io.Serializable
import java.time.LocalDate

data class Schedule(
    val id: Int = 0,
    val title: String?,
    val start_date: LocalDate?,
    val end_date: LocalDate?,
    val color: Int?,
    val success: Boolean? = false
): Serializable
