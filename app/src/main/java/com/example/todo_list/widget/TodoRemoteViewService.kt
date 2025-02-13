package com.example.todo_list.widget

import android.content.Intent
import android.widget.RemoteViewsService
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class RoutineRemoteViewsService: RemoteViewsService() {
    @Inject lateinit var factory: RoutineRemoteViewsFactory
    override fun onGetViewFactory(intent: Intent?): RemoteViewsFactory {
        return factory
    }
}

@AndroidEntryPoint
class ScheduleRemoteViewsService: RemoteViewsService(){
    @Inject lateinit var factory: ScheduleRemoteViewsFactory
    override fun onGetViewFactory(intent: Intent?): RemoteViewsFactory {
        return factory
    }
}
