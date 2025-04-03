package com.example.todo_list.di

import android.content.Context
import com.example.todo_list.data.repository.routine.RoutineRepository
import com.example.todo_list.data.repository.routine.RoutineRepositoryImpl
import com.example.todo_list.data.repository.schedule.ScheduleRepository
import com.example.todo_list.data.repository.schedule.ScheduleRepositoryImpl
import com.example.todo_list.data.room.DataBase
import com.example.todo_list.data.room.RoutineDAO
import com.example.todo_list.data.room.ScheduleDAO
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LocalDatabaseModules {
    @Singleton
    @Provides
    fun provideDatabase(
        @ApplicationContext context: Context
    ): DataBase = DataBase.getInstance(context)


    @Singleton
    @Provides
    fun provideScheduleDao(
        database: DataBase
    ): ScheduleDAO = database.scheduleDao

    @Singleton
    @Provides
    fun provideRoutineDAO(
        database: DataBase
    ): RoutineDAO = database.routineDao

    @Singleton
    @Provides
    fun provideScheduleRepo(
        scheduleDAO: ScheduleDAO
    ): ScheduleRepository = ScheduleRepositoryImpl(scheduleDAO)

    @Singleton
    @Provides
    fun provideRoutineRepo(
        routineDAO: RoutineDAO
    ): RoutineRepository = RoutineRepositoryImpl(routineDAO)
}

@Module
@InstallIn(ActivityComponent::class)
abstract class RepositoryModules {
    @Binds
    abstract fun bindScheduleRepository(
        scheduleRepositoryImpl: ScheduleRepositoryImpl
    ): ScheduleRepository

    @Binds
    abstract fun bindRoutineRepository(
        routineRepositoryImpl: RoutineRepositoryImpl
    ): RoutineRepository
}
