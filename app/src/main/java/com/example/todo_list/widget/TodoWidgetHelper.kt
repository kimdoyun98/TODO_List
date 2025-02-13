package com.example.todo_list.widget

import android.content.Context
import android.content.Intent
import android.widget.RemoteViews
import com.example.todo_list.R
import java.time.LocalDate

class TodoWidgetHelper(
    private val context: Context?,
    val widget: RemoteViews
) {
    private val localDate: LocalDate = LocalDate.now()

    fun setTodayDate(): TodoWidgetHelper {
        widget.setTextViewText(R.id.widget_today_tv, localDate.toString())
        return TodoWidgetHelper(context, widget)
    }

    fun setWeekSchedule(): TodoWidgetHelper {
        val serviceIntent = Intent(context, ScheduleRemoteViewsService::class.java)
        widget.setRemoteAdapter(R.id.widget_schedule_lv, serviceIntent)
        return TodoWidgetHelper(context, widget)
    }

    fun setTodayRoutine(): TodoWidgetHelper {
        val serviceIntent = Intent(context, RoutineRemoteViewsService::class.java)
        widget.setRemoteAdapter(R.id.widget_routine_lv, serviceIntent)
        return TodoWidgetHelper(context, widget)
    }

}
