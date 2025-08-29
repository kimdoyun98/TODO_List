package com.project.data.model

import com.project.database.entity.StatisticsLogEntity
import com.project.model.StatisticsLog

fun StatisticsLog.asEntity() = StatisticsLogEntity(
    id = id,
    routineLogId = routineLogId,
    total = total,
    success = success
)
