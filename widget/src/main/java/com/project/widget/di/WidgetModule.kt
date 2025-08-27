package com.project.widget.di

import android.content.Context
import com.project.data.repository.log.RoutineLogRepository
import com.project.widget.routine.RoutineRemoteViewsFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

object WidgetModule {
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
}
