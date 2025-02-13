package com.example.todo_list.widget

import android.content.Intent
import android.widget.RemoteViewsService
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class TodoRemoteViewService: RemoteViewsService() {
    @Inject lateinit var factory: TodoRemoteViewFactory
    override fun onGetViewFactory(intent: Intent?): RemoteViewsFactory {
        return factory
    }
}
