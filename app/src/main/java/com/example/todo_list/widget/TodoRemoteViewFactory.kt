package com.example.todo_list.widget

import android.content.Context
import android.util.Log
import android.widget.RemoteViews
import android.widget.RemoteViewsService
import com.example.todo_list.R
import com.example.todo_list.data.repository.routine.RoutineRepository
import com.example.todo_list.data.room.RoutineEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import java.util.Calendar


class TodoRemoteViewFactory(
    private val context: Context,
    private val repo: RoutineRepository
) : RemoteViewsService.RemoteViewsFactory {
    private var data: List<RoutineEntity> = emptyList()
    private var scope: Job? = null

    override fun onCreate() {
        val today = Calendar.getInstance().get(Calendar.DAY_OF_WEEK)
        scope = CoroutineScope(Dispatchers.IO).launch {
            repo.selectAll()
                .onEach { routine ->
                    routine.filter {
                        it.day!![today - 1]
                    }.sortedBy {
                        it.time
                    }
                }
                .collect {
                    data = it
                }
        }
    }

    override fun onDataSetChanged() {
        val today = Calendar.getInstance().get(Calendar.DAY_OF_WEEK)
        scope = CoroutineScope(Dispatchers.IO).launch {
            repo.selectAll()
                .onEach { routine ->
                    routine.filter {
                        it.day!![today - 1]
                    }.sortedBy {
                        it.time
                    }
                }
                .collect {
                    data = it
                }
        }
    }

    override fun onDestroy() {
        scope = null
    }

    override fun getCount(): Int = data.size

    override fun getViewAt(position: Int): RemoteViews {
        val listviewWidget = RemoteViews(context.packageName, R.layout.todo_widget_item)
        listviewWidget.setTextViewText(R.id.widget_routine, data[position].title)

        return listviewWidget
    }

    override fun getLoadingView(): RemoteViews {
        return RemoteViews(context.packageName, R.layout.todo_widget_loading)
    }

    override fun getViewTypeCount(): Int {
        return 1
    }

    override fun getItemId(position: Int): Long {
        return 0
    }

    override fun hasStableIds(): Boolean {
        return false
    }
}
