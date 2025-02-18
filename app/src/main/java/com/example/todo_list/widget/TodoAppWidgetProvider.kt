package com.example.todo_list.widget

import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.widget.RemoteViews
import com.example.todo_list.R

class TodoAppWidgetProvider : AppWidgetProvider() {
    override fun onUpdate(
        context: Context?,
        appWidgetManager: AppWidgetManager?,
        appWidgetIds: IntArray?
    ) {
        super.onUpdate(context, appWidgetManager, appWidgetIds)
        val todoWidgetHelper =
            TodoWidgetHelper(context, RemoteViews(context?.packageName, R.layout.todo_widget))
                .setTodayRoutine()
                .setWeekSchedule()
                .setTodayDate()

        appWidgetManager?.updateAppWidget(appWidgetIds, todoWidgetHelper.widget)
        appWidgetManager?.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.widget_routine_lv)
        appWidgetManager?.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.widget_schedule_lv)
    }
}
