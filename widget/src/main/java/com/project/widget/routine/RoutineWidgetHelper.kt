package com.project.widget.routine

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.widget.RemoteViews
import com.project.main.MainActivity
import com.project.widget.R
import com.project.widget.RoutineRemoteViewsService

class RoutineWidgetHelper(
    private val context: Context?,
    val widget: RemoteViews
) {
    fun setTodayRoutine(): RoutineWidgetHelper {
        val serviceIntent = Intent(context, RoutineRemoteViewsService::class.java)
        widget.setRemoteAdapter(R.id.widget_routine_lv, serviceIntent)
        return RoutineWidgetHelper(context, widget)
    }

    fun setOnClickWidget(): RoutineWidgetHelper {
        val pendingIntent = PendingIntent.getActivity(
            context,
            0,
            Intent(context, MainActivity::class.java),
            PendingIntent.FLAG_IMMUTABLE
        )
        widget.setOnClickPendingIntent(R.id.widget, pendingIntent)

        return RoutineWidgetHelper(context, widget)
    }

    fun setOnListViewClick(): RoutineWidgetHelper {
        val pendingIntent = PendingIntent.getActivity(
            context,
            0,
            Intent(context, MainActivity::class.java),
            PendingIntent.FLAG_MUTABLE
        )

        widget.setPendingIntentTemplate(R.id.widget_routine_lv, pendingIntent)

        return RoutineWidgetHelper(context, widget)
    }
}
