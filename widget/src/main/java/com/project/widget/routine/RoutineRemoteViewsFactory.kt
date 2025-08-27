package com.project.widget.routine

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.widget.RemoteViews
import android.widget.RemoteViewsService
import com.project.data.local.room.entity.RoutineEntity
import com.project.data.repository.log.RoutineLogRepository
import com.project.widget.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.time.LocalDate


class RoutineRemoteViewsFactory(
    private val context: Context,
    private val repository: RoutineLogRepository
) : RemoteViewsService.RemoteViewsFactory {
    private var scope = CoroutineScope(Dispatchers.IO)
    private var job: Job? = null
    private var routineList: List<RoutineEntity> = emptyList()

    override fun onCreate() {
        job = scope.launch { getRoutines() }


    }

    override fun onDataSetChanged() {
        job = scope.launch { getRoutines() }
    }

    override fun getCount(): Int {
        return if (routineList.isEmpty()) 1 else routineList.size
    }

    override fun getViewAt(position: Int): RemoteViews {
        val listviewWidget = RemoteViews(context.packageName, R.layout.widget_routine_item)

        listviewWidget.setOnClickFillInIntent(R.id.routine_item_root, Intent())

        if (routineList.isEmpty()) {
            listviewWidget.setTextViewText(
                R.id.widget_routine_title,
                NO_ROUTINE
            )

            return listviewWidget
        }

        val routineData = routineList[position]

        listviewWidget.setTextViewText(
            R.id.widget_routine_title,
            routineData.title
        )

        listviewWidget.setTextViewText(
            R.id.widget_routine_time,
            routineData.time
        )

        when (routineData.success) {
            true -> setTextColor(
                listviewWidget,
                context.getColor(com.project.core.ui.R.color.green)
            )

            false -> setTextColor(listviewWidget, Color.RED)
            else -> {
                if (position == 0 || routineList[position - 1].success != null) {
                    setTextColor(listviewWidget, Color.BLUE)
                } else {
                    setTextColor(listviewWidget, Color.GRAY)
                }
            }
        }

        return listviewWidget
    }

    private fun setTextColor(listViewWidget: RemoteViews, color: Int) {
        listViewWidget.setTextColor(R.id.widget_routine_title, color)
        listViewWidget.setTextColor(R.id.widget_routine_time, color)
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

    private suspend fun getRoutines() {
        val routineLog = repository.getWidgetTodayLog(LocalDate.now())
        val routines = routineLog?.routines?.values?.toList() ?: emptyList()

        routineList = routines.sortedBy { it.time }
    }

    companion object {
        private const val NOT_LOADING = "Error!! Not Loading Today Routine"
        private const val NO_ROUTINE = "일정이 없습니다."
    }
}
