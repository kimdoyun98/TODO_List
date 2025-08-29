package com.project.data.model

import com.project.database.entity.ScheduleEntity
import com.project.model.Schedule

fun Schedule.asEntity() = ScheduleEntity(
    id = id,
    title = title,
    start_date = start_date,
    end_date = end_date,
    color = color,
    success = success
)
