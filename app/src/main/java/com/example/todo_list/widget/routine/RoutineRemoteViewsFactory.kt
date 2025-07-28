package com.example.todo_list.widget.routine

import android.content.Context
import android.graphics.Color
import android.widget.RemoteViews
import android.widget.RemoteViewsService
import com.example.todo_list.R
import com.example.todo_list.data.repository.log.RoutineLogRepository
import com.example.todo_list.data.room.RoutineEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.time.LocalDate


class RoutineRemoteViewsFactory(
    private val context: Context,
    private val repository: RoutineLogRepository
) : RemoteViewsService.RemoteViewsFactory {
    private var widgetRoutineData: WidgetRoutineData = WidgetRoutineData.IsEmptyRoutine()
    private var scope = CoroutineScope(Dispatchers.IO)
    private var job: Job? = null

    override fun onCreate() {

    }

    override fun onDataSetChanged() {
        widgetRoutineData = WidgetRoutineData.Idle
        job = scope.launch {
            val routineLog = repository.getWidgetTodayLog(LocalDate.now())
            val routines = routineLog?.routines?.values?.toList() ?: emptyList()

            val todayRoutines = routines.sortedBy { it.time }
            widgetRoutineData =
                if (todayRoutines.isNotEmpty()) WidgetRoutineData.IsNotEmptyRoutine(todayRoutines)
                else WidgetRoutineData.IsEmptyRoutine()
        }
    }

    override fun getCount(): Int {
        while (true) {
            if (widgetRoutineData !is WidgetRoutineData.Idle) break
        }

        return when (widgetRoutineData) {
            is WidgetRoutineData.IsEmptyRoutine -> {
                1
            }

            is WidgetRoutineData.IsNotEmptyRoutine -> {
                (widgetRoutineData as WidgetRoutineData.IsNotEmptyRoutine).data.size
            }

            else -> throw Exception(NOT_LOADING)
        }
    }

    override fun getViewAt(position: Int): RemoteViews {
        val listviewWidget = RemoteViews(context.packageName, R.layout.widget_routine_item)
        when (widgetRoutineData) {
            is WidgetRoutineData.IsEmptyRoutine -> {
                listviewWidget.setTextViewText(
                    R.id.widget_routine_title,
                    (widgetRoutineData as WidgetRoutineData.IsEmptyRoutine).data[position]
                )
            }

            is WidgetRoutineData.IsNotEmptyRoutine -> {
                val routineData =
                    (widgetRoutineData as WidgetRoutineData.IsNotEmptyRoutine).data[position]

                listviewWidget.setTextViewText(
                    R.id.widget_routine_title,
                    routineData.title
                )

                listviewWidget.setTextViewText(
                    R.id.widget_routine_time,
                    routineData.time
                )

                if (routineData.success == true) {
                    listviewWidget.setTextColor(R.id.widget_routine_title, Color.GRAY)
                    listviewWidget.setTextColor(R.id.widget_routine_time, Color.GRAY)
                } else {
                    listviewWidget.setTextColor(R.id.widget_routine_title, Color.WHITE)
                    listviewWidget.setTextColor(R.id.widget_routine_time, Color.WHITE)
                }
            }

            else -> {
                listviewWidget.setTextViewText(
                    R.id.widget_routine_title,
                    NOT_LOADING
                )
            }
        }

        return listviewWidget
    }

    override fun getLoadingView(): RemoteViews {
        return RemoteViews(context.packageName, R.layout.widget_loading)
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

    override fun onDestroy() {
        job = null
    }

    companion object {
        private const val NOT_LOADING = "Error!! Not Loading Today Routine"
    }
}

sealed interface WidgetRoutineData {

    data object Idle : WidgetRoutineData

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
