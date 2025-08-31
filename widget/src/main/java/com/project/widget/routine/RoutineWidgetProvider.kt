package com.project.widget.routine

import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.widget.RemoteViews
import com.project.widget.R

class RoutineWidgetProvider : AppWidgetProvider() {
    override fun onUpdate(
        context: Context?,
        appWidgetManager: AppWidgetManager?,
        appWidgetIds: IntArray?
    ) {
        super.onUpdate(context, appWidgetManager, appWidgetIds)
        val routineWidgetHelper =
            RoutineWidgetHelper(context, RemoteViews(context?.packageName, R.layout.widget_routine))
                .setTodayRoutine()
                .setOnClickWidget()
                .setOnListViewClick()

        appWidgetManager?.updateAppWidget(appWidgetIds, routineWidgetHelper.widget)
        appWidgetManager?.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.widget_routine_lv)
    }
}
