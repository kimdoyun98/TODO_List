package com.example.todo_list.widget

import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.os.Bundle
import android.widget.RemoteViews
import com.example.todo_list.R

class TodoAppWidgetProvider : AppWidgetProvider() {
    override fun onEnabled(context: Context?) {
        super.onEnabled(context)
    }

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
    }

    override fun onAppWidgetOptionsChanged(
        context: Context?,
        appWidgetManager: AppWidgetManager?,
        appWidgetId: Int,
        newOptions: Bundle?
    ) {
        super.onAppWidgetOptionsChanged(context, appWidgetManager, appWidgetId, newOptions)
    }
}
