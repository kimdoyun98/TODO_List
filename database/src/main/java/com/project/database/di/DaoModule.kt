package com.project.database.di

import com.project.database.DataBase
import com.project.database.dao.RoutineDAO
import com.project.database.dao.RoutineLogDAO
import com.project.database.dao.ScheduleDAO
import com.project.database.dao.StatisticsLogDAO
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
