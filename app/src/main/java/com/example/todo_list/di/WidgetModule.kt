package com.example.todo_list.di

import android.content.Context
import com.example.todo_list.data.repository.log.RoutineLogRepository
import com.example.todo_list.widget.routine.RoutineRemoteViewsFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object WidgetModule {

    @Provides
    @Singleton
    fun providerTodoRemoteViewFactory(
        @ApplicationContext context: Context,
        routineLogRepository: RoutineLogRepository
    ): RoutineRemoteViewsFactory = RoutineRemoteViewsFactory(context, routineLogRepository)
}
