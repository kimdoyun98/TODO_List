package com.example.todo_list.data

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object Module {
    @Singleton
    @Provides
    fun provideDatabase(
        @ApplicationContext context: Context
    ): DataBase = DataBase.getInstance(context)


    @Singleton
    @Provides
    fun provideRepository(database: DataBase
    ): ScheduleDAO = database.scheduleDao

    @Singleton
    @Provides
    fun provideRoutineRepository(database: DataBase
    ): RoutineDAO = database.routineDao
}