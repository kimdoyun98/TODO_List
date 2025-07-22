package com.example.todo_list.di

import android.content.Context
import com.example.todo_list.data.repository.log.RoutineLogRepository
import com.example.todo_list.data.repository.log.RoutineLogRepositoryImpl
import com.example.todo_list.data.repository.log.StatisticsLogRepository
import com.example.todo_list.data.repository.log.StatisticsLogRepositoryImpl
import com.example.todo_list.data.repository.routine.RoutineRepository
import com.example.todo_list.data.repository.routine.RoutineRepositoryImpl
import com.example.todo_list.data.repository.schedule.ScheduleRepository
import com.example.todo_list.data.repository.schedule.ScheduleRepositoryImpl
import com.example.todo_list.data.room.DataBase
import com.example.todo_list.data.room.dao.RoutineDAO
import com.example.todo_list.data.room.dao.RoutineLogDAO
import com.example.todo_list.data.room.dao.ScheduleDAO
import com.example.todo_list.data.room.dao.StatisticsLogDAO
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
    fun provideRoutineLogDAO(
        database: DataBase
    ): RoutineLogDAO = database.routineLogDao

    @Singleton
    @Provides
    fun provideStatisticsLogDAO(
        database: DataBase
    ): StatisticsLogDAO = database.statisticsLogDao

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

    @Singleton
    @Provides
    fun provideRoutineLogRepo(
        routineLogDao: RoutineLogDAO
    ): RoutineLogRepository = RoutineLogRepositoryImpl(routineLogDao)

    @Singleton
    @Provides
    fun provideStatisticsLogRepo(
        statisticsLogDao: StatisticsLogDAO
    ): StatisticsLogRepository = StatisticsLogRepositoryImpl(statisticsLogDao)
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
