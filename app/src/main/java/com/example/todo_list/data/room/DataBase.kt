package com.example.todo_list.data.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [ScheduleEntity::class, RoutineEntity::class], version = 4, exportSchema = false)
@TypeConverters(DayListConverter::class)
abstract class DataBase : RoomDatabase() {
    abstract val scheduleDao: ScheduleDAO
    abstract val routineDao: RoutineDAO

    companion object {
        private var INSTANCE: DataBase? = null

        fun getInstance(context: Context): DataBase {
            return INSTANCE ?: synchronized(DataBase::class) { // 여러 Thread 가 접근하지 못하도록 Synchronized 사용
                // Room 인스턴스 생성
                // 데이터 베이스가 갱신될 때 기존의 테이블을 버리고 새로 사용하도록 설정
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    DataBase::class.java, "schedule"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}