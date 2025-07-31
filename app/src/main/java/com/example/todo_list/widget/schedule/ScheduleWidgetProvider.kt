package com.example.todo_list.widget.schedule

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.widget.RemoteViews
import com.example.todo_list.R
import com.example.todo_list.data.repository.schedule.ScheduleRepository
import com.example.todo_list.ui.MainActivity
import com.example.todo_list.util.DateCalculate
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class ScheduleWidgetProvider : AppWidgetProvider() {
    @Inject
    lateinit var repository: ScheduleRepository
    private var job: Job? = null
    private var scope = CoroutineScope(Dispatchers.IO)
    private val dateCalculate = DateCalculate

    override fun onUpdate(
        context: Context?,
        appWidgetManager: AppWidgetManager?,
        appWidgetIds: IntArray?
    ) {
        super.onUpdate(context, appWidgetManager, appWidgetIds)
        job = scope.launch {
            val recentSchedule = repository.getWidgetRecentSchedule()
            val title = recentSchedule?.title ?: "일정 없음"
            val periodDay =
                if (recentSchedule == null) "" else dateCalculate.getDDayString(recentSchedule.start_date)

            val remoteViews = RemoteViews(context?.packageName, R.layout.widget_schedule)
            val pendingIntent = PendingIntent.getActivity(
                context,
                0,
                Intent(context, MainActivity::class.java),
                PendingIntent.FLAG_IMMUTABLE
            )

            remoteViews.setOnClickPendingIntent(R.id.schedule_layout_widget, pendingIntent)
            remoteViews.setTextViewText(R.id.schedule_title_widget, title)
            remoteViews.setTextViewText(R.id.schedule_period_date_widget, periodDay)

            appWidgetManager?.updateAppWidget(appWidgetIds, remoteViews)
        }
    }

    override fun onDisabled(context: Context?) {
        super.onDisabled(context)
        scope.cancel()
        job = null
    }
}
