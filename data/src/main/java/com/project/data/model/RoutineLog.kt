package com.project.data.model

import com.project.database.entity.RoutineLogEntity
import com.project.model.RoutineLog

fun RoutineLog.asEntity() = RoutineLogEntity(
    id = id,
    date = date,
    routines = routines
)
