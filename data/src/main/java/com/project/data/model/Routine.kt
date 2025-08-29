package com.project.data.model

import com.project.database.entity.RoutineEntity
import com.project.model.Routine

fun Routine.asEntity() = RoutineEntity(
    id = id,
    title = title,
    day = day,
    success = success,
    time = time
)
