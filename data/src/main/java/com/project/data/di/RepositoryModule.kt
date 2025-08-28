package com.project.data.di

import com.project.data.repository.log.RoutineLogRepository
import com.project.data.repository.log.RoutineLogRepositoryImpl
import com.project.data.repository.log.StatisticsLogRepository
import com.project.data.repository.log.StatisticsLogRepositoryImpl
import com.project.data.repository.routine.RoutineRepository
import com.project.data.repository.routine.RoutineRepositoryImpl
import com.project.data.repository.schedule.ScheduleRepository
import com.project.data.repository.schedule.ScheduleRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    internal abstract fun bindScheduleRepository(
        scheduleRepositoryImpl: ScheduleRepositoryImpl
    ): ScheduleRepository

    @Binds
    internal abstract fun bindRoutineRepository(
        routineRepositoryImpl: RoutineRepositoryImpl
    ): RoutineRepository

    @Binds
    internal abstract fun bindRoutineLogRepository(
        routineLogRepositoryImpl: RoutineLogRepositoryImpl
    ): RoutineLogRepository

    @Binds
    internal abstract fun bindStatisticsLogRepository(
        statisticsLogRepositoryImpl: StatisticsLogRepositoryImpl
    ): StatisticsLogRepository
}
