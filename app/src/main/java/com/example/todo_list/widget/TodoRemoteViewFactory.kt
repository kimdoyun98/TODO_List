package com.example.todo_list.widget

import android.content.Context
import android.widget.RemoteViews
import android.widget.RemoteViewsService
import com.example.todo_list.R
import com.example.todo_list.data.repository.routine.RoutineRepository
import com.example.todo_list.data.room.RoutineEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch


class TodoRemoteViewFactory(
    private val context: Context,
    private val repo: RoutineRepository
) : RemoteViewsService.RemoteViewsFactory {
    private var widgetRoutineData: WidgetRoutineData = WidgetRoutineData.IsEmptyRoutine()
    private var scope: Job? = null

    override fun onCreate() {
        scope = CoroutineScope(Dispatchers.IO).launch {
            repo.getTodayRoutine()
                .collect {
                    widgetRoutineData = if (it.isNotEmpty()) WidgetRoutineData.IsNotEmptyRoutine(it)
                    else WidgetRoutineData.IsEmptyRoutine()
                }
        }
    }

    override fun onDataSetChanged() {
        scope = CoroutineScope(Dispatchers.IO).launch {
            repo.getTodayRoutine()
                .collect {
                    widgetRoutineData = if (it.isNotEmpty()) WidgetRoutineData.IsNotEmptyRoutine(it)
                    else WidgetRoutineData.IsEmptyRoutine()
                }
        }
    }

    override fun onDestroy() {
        scope = null
    }

    override fun getCount(): Int {
        return when (widgetRoutineData) {
            is WidgetRoutineData.IsEmptyRoutine -> {
                1
            }

            is WidgetRoutineData.IsNotEmptyRoutine -> {
                (widgetRoutineData as WidgetRoutineData.IsNotEmptyRoutine).data.size
            }
        }
    }

    override fun getViewAt(position: Int): RemoteViews {
        val listviewWidget = RemoteViews(context.packageName, R.layout.todo_widget_item)
        when (widgetRoutineData) {
            is WidgetRoutineData.IsEmptyRoutine -> {
                listviewWidget.setTextViewText(
                    R.id.widget_routine,
                    (widgetRoutineData as WidgetRoutineData.IsEmptyRoutine).data[position]
                )
            }

            is WidgetRoutineData.IsNotEmptyRoutine -> {
                listviewWidget.setTextViewText(
                    R.id.widget_routine,
                    (widgetRoutineData as WidgetRoutineData.IsNotEmptyRoutine).data[position].title
                )
            }
        }

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

sealed interface WidgetRoutineData {

    data class IsEmptyRoutine(
        val data: List<String> = listOf(NO_ROUTINE)
    ) : WidgetRoutineData

    data class IsNotEmptyRoutine(
        val data: List<RoutineEntity>
    ) : WidgetRoutineData

    companion object {
        private const val NO_ROUTINE = "일정을 추가해주세요."
    }
}
