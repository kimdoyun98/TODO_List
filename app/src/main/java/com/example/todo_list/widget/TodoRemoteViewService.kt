package com.example.todo_list.widget

import android.content.Intent
import android.widget.RemoteViewsService
import com.example.todo_list.widget.routine.RoutineRemoteViewsFactory
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class RoutineRemoteViewsService: RemoteViewsService() {
    @Inject lateinit var factory: RoutineRemoteViewsFactory
    override fun onGetViewFactory(intent: Intent?): RemoteViewsFactory {
        return factory
    }
}
