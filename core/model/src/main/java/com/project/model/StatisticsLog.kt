package com.project.model

data class StatisticsLog(
    val id: Int = 0,
    val routineLogId: Int?,
    val total: Int,
    val success: Int,
)
