package com.example.todo_list.widget

import android.content.Context
import android.widget.RemoteViews
import android.widget.RemoteViewsService
import com.example.todo_list.R
import com.example.todo_list.data.repository.schedule.ScheduleRepository
import com.example.todo_list.data.room.ScheduleEntity
import com.example.todo_list.util.DateCalculate
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class ScheduleRemoteViewsFactory(
    private val context: Context,
    private val repo: ScheduleRepository
) : RemoteViewsService.RemoteViewsFactory {
    private var widgetScheduleData: WidgetScheduleData = WidgetScheduleData.Idle

    private var job: Job? = null
    private var scope = CoroutineScope(Dispatchers.IO)
    private val dateCalculate = DateCalculate
    private val weekSchedule = repo.selectAll()
        .stateIn(
            scope,
            SharingStarted.WhileSubscribed(5_000L),
            emptyList()
        )

    override fun onCreate() {
        job = scope.launch {
            weekSchedule.collect { scheduleList ->
                val weekSchedule = scheduleList
                    .filter {
                        dateCalculate.isWeekSchedule(it.deadline_date)
                    }
                    .sortedBy {
                        dateCalculate.getDDay(it.deadline_date)
                    }

                widgetScheduleData = WidgetScheduleData.Schedule(weekSchedule)
            }
        }
    }

    override fun onDataSetChanged() {
        widgetScheduleData = WidgetScheduleData.Idle
        job = scope.launch {
            weekSchedule.collect { scheduleList ->
                val weekSchedule = scheduleList
                    .filter {
                        dateCalculate.isWeekSchedule(it.deadline_date)
                    }
                    .sortedBy {
                        dateCalculate.getDDay(it.deadline_date)
                    }

                widgetScheduleData = WidgetScheduleData.Schedule(weekSchedule)
            }
        }
    }

    override fun getCount(): Int {
        while (true) {
            if (widgetScheduleData !is WidgetScheduleData.Idle) break
        }

        return (widgetScheduleData as WidgetScheduleData.Schedule).data.size
    }

    override fun getViewAt(position: Int): RemoteViews {
        val listviewWidget = RemoteViews(context.packageName, R.layout.todo_widget_schedule_item)

        when (widgetScheduleData) {
            is WidgetScheduleData.Schedule -> {
                listviewWidget.setTextViewText(
                    R.id.widget_schedule_title_tv,
                    (widgetScheduleData as WidgetScheduleData.Schedule).data[position].title
                )

                listviewWidget.setTextViewText(
                    R.id.widget_schedule_day_tv,
                    dateCalculate.getDDayString(
                        (widgetScheduleData as WidgetScheduleData.Schedule).data[position].deadline_date
                    )
                )
            }

            else -> {
                listviewWidget.setTextViewText(
                    R.id.widget_schedule_title_tv,
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
        private const val NOT_LOADING = "Error!! Not Loading Week Schedule"
    }
}

sealed interface WidgetScheduleData {

    data object Idle : WidgetScheduleData

    data class Schedule(
        val data: List<ScheduleEntity>
    ) : WidgetScheduleData

}
