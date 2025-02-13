package com.example.todo_list.widget

import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.RemoteViews
import com.example.todo_list.R
import java.time.LocalDate

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
        val localDate: LocalDate = LocalDate.now()
        val serviceIntent = Intent(context, TodoRemoteViewService::class.java)
        val widget = RemoteViews(context?.packageName, R.layout.todo_widget)
        widget.setRemoteAdapter(R.id.widget_lv, serviceIntent)
        widget.setTextViewText(R.id.widget_today_tv, localDate.toString())

        appWidgetManager?.updateAppWidget(appWidgetIds, widget)
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
