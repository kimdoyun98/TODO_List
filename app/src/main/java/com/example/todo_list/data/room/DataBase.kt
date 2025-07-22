package com.example.todo_list.data.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.todo_list.data.room.dao.RoutineDAO
import com.example.todo_list.data.room.dao.ScheduleDAO

@Database(
    entities = [
        ScheduleEntity::class,
        RoutineEntity::class,
        RoutineDetailEntity::class,
        RoutineLog::class,
        StatisticsLog::class
    ],
    version = 3,
    exportSchema = false
)
@TypeConverters(DayListConverter::class)
abstract class DataBase : RoomDatabase() {
    abstract val scheduleDao: ScheduleDAO
    abstract val routineDao: RoutineDAO

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
