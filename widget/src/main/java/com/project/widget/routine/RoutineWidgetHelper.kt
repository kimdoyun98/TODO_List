package com.project.widget.routine

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.widget.RemoteViews
import com.project.widget.R
import com.project.widget.RoutineRemoteViewsService

internal class RoutineWidgetHelper(
    private val context: Context?,
    val widget: RemoteViews
) {
    fun setTodayRoutine(): RoutineWidgetHelper {
        val serviceIntent = Intent(context, RoutineRemoteViewsService::class.java)
        widget.setRemoteAdapter(R.id.widget_routine_lv, serviceIntent)
        return RoutineWidgetHelper(context, widget)
    }

    fun setOnClickWidget(): RoutineWidgetHelper {
        val intent = Intent()
        intent.setClassName(context!!, MAIN_ACTIVITY)
        val pendingIntent = PendingIntent.getActivity(
            context,
            0,
            intent,
            PendingIntent.FLAG_IMMUTABLE
        )
        widget.setOnClickPendingIntent(R.id.widget, pendingIntent)

        return RoutineWidgetHelper(context, widget)
    }

    fun setOnListViewClick(): RoutineWidgetHelper {
        val intent = Intent()
        intent.setClassName(context!!, MAIN_ACTIVITY)

        val pendingIntent = PendingIntent.getActivity(
            context,
            0,
            intent,
            PendingIntent.FLAG_MUTABLE
        )

        widget.setPendingIntentTemplate(R.id.widget_routine_lv, pendingIntent)

        return RoutineWidgetHelper(context, widget)
    }

    companion object {
        private const val MAIN_ACTIVITY = "com.project.main.MainActivity"
    }
}
