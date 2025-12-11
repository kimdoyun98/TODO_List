package com.project.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.project.database.dao.RoutineDAO
import com.project.database.dao.RoutineLogDAO
import com.project.database.dao.ScheduleDAO
import com.project.database.dao.StatisticsLogDAO
import com.project.database.entity.RoutineEntity
import com.project.database.entity.RoutineLogEntity
import com.project.database.entity.ScheduleEntity
import com.project.database.entity.StatisticsLogEntity

@Database(
    entities = [
        ScheduleEntity::class,
        RoutineEntity::class,
        RoutineLogEntity::class,
        StatisticsLogEntity::class
    ],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converter::class)
internal abstract class DataBase : RoomDatabase() {
    abstract val scheduleDao: ScheduleDAO
    abstract val routineDao: RoutineDAO
    abstract val routineLogDao: RoutineLogDAO
    abstract val statisticsLogDao: StatisticsLogDAO

    companion object {
        private var INSTANCE: DataBase? = null
        private const val SCHEDULE = "schedule"

        fun getInstance(context: Context): DataBase {
            return INSTANCE
                ?: synchronized(DataBase::class) { // 여러 Thread 가 접근하지 못하도록 Synchronized 사용
                    // Room 인스턴스 생성
                    // 데이터 베이스가 갱신될 때 기존의 테이블을 버리고 새로 사용하도록 설정
                    val instance = Room.databaseBuilder(
                        context.applicationContext,
                        DataBase::class.java, SCHEDULE
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                    INSTANCE = instance
                    instance
                }
        }
    }
}
