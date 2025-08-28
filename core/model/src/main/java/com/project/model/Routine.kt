package com.project.model

data class Routine(
    val id: Int,
    val title: String,
    val day: List<Boolean>,
    var success: Boolean? = null,
    var time: String
)
