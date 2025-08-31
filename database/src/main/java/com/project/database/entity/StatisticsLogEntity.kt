package com.project.database.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.project.model.StatisticsLog

@Entity(
    tableName = "StatisticsLog",
    foreignKeys = [
        ForeignKey(
            entity = RoutineLogEntity::class,
            parentColumns = ["id"],
            childColumns = ["routineLogId"],
            onDelete = ForeignKey.NO_ACTION
        )
    ]
)
data class StatisticsLogEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val routineLogId: Int?,
    val total: Int,
    val success: Int,
)

fun StatisticsLogEntity.asExternalModel() = StatisticsLog(
    id = id,
    routineLogId = routineLogId,
    total = total,
    success = success
)
