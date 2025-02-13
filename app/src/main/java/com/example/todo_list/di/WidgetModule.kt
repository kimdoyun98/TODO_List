package com.example.todo_list.di

import android.content.Context
import com.example.todo_list.data.repository.routine.RoutineRepository
import com.example.todo_list.widget.TodoRemoteViewFactory
import com.example.todo_list.widget.TodoRemoteViewService
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
        routineRepository: RoutineRepository
    ): TodoRemoteViewFactory = TodoRemoteViewFactory(context, routineRepository)

}
