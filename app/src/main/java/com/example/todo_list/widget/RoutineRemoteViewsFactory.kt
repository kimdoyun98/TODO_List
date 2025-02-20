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
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.util.Calendar


class RoutineRemoteViewsFactory(
    private val context: Context,
    private val repo: RoutineRepository
) : RemoteViewsService.RemoteViewsFactory {
    private var widgetRoutineData: WidgetRoutineData = WidgetRoutineData.IsEmptyRoutine()
    private var scope = CoroutineScope(Dispatchers.IO)
    private var job: Job? = null
    private val allRoutine = repo.selectAll()
        .stateIn(
            scope,
            SharingStarted.WhileSubscribed(5_000L),
            emptyList()
        )

    override fun onCreate() {
        job = scope.launch {
            allRoutine.collect { routineList ->
                val today = Calendar.getInstance().get(Calendar.DAY_OF_WEEK)
                val todayRoutine = routineList
                    .filter {
                        it.day?.get(today - 1) ?: false
                    }.sortedBy {
                        it.time
                    }

                widgetRoutineData =
                    if (todayRoutine.isNotEmpty()) WidgetRoutineData.IsNotEmptyRoutine(todayRoutine)
                    else WidgetRoutineData.IsEmptyRoutine()
            }
        }
    }

    override fun onDataSetChanged() {
        widgetRoutineData = WidgetRoutineData.Idle
        job = scope.launch {
            allRoutine.collect { routineList ->
                val today = Calendar.getInstance().get(Calendar.DAY_OF_WEEK)
                val todayRoutine = routineList
                    .filter {
                        it.day?.get(today - 1) ?: false
                    }.sortedBy {
                        it.time
                    }

                widgetRoutineData =
                    if (todayRoutine.isNotEmpty()) WidgetRoutineData.IsNotEmptyRoutine(todayRoutine)
                    else WidgetRoutineData.IsEmptyRoutine()
            }
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
        val listviewWidget = RemoteViews(context.packageName, R.layout.todo_widget_routine_item)
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

            else -> {
                listviewWidget.setTextViewText(
                    R.id.widget_routine,
                    NOT_LOADING
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
