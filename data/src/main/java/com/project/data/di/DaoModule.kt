package com.project.data.di

import com.project.data.local.room.DataBase
import com.project.data.local.room.dao.RoutineDAO
import com.project.data.local.room.dao.RoutineLogDAO
import com.project.data.local.room.dao.ScheduleDAO
import com.project.data.local.room.dao.StatisticsLogDAO
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DaoModule {
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
}
