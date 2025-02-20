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
    private var widgetScheduleData: List<ScheduleEntity> = emptyList()

    private var job: Job? = null
    private var scope = CoroutineScope(Dispatchers.IO)
    private val dateCalculate = DateCalculate()
    private val weekSchedule = repo.selectAll()
        .stateIn(
            scope,
            SharingStarted.WhileSubscribed(5_000L),
            emptyList()
        )

    override fun onCreate() {
        job = scope.launch {
            weekSchedule.collect { scheduleList ->
                widgetScheduleData = scheduleList
                    .filter {
                        dateCalculate.isWeekSchedule(it.deadline_date)
                    }
                    .sortedBy {
                        dateCalculate.getDDay(it.deadline_date)
                    }
            }
        }
    }

    override fun onDataSetChanged() {
        job = scope.launch {
            weekSchedule.collect { scheduleList ->
                widgetScheduleData = scheduleList
                    .filter {
                        dateCalculate.isWeekSchedule(it.deadline_date)
                    }
                    .sortedBy {
                        dateCalculate.getDDay(it.deadline_date)
                    }
            }
        }
    }

    override fun onDestroy() {
        job = null
    }

    override fun getCount(): Int = widgetScheduleData.size

    override fun getViewAt(position: Int): RemoteViews {
        val listviewWidget = RemoteViews(context.packageName, R.layout.todo_widget_schedule_item)

        listviewWidget.setTextViewText(
            R.id.widget_schedule_title_tv, widgetScheduleData[position].title
        )

        listviewWidget.setTextViewText(
            R.id.widget_schedule_day_tv,
            dateCalculate.getDDayString(widgetScheduleData[position].deadline_date)
        )

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
