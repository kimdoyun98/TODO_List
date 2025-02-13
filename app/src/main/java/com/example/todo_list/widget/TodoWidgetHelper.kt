package com.example.todo_list.widget

import android.content.Context
import android.content.Intent
import android.widget.RemoteViews
import com.example.todo_list.R
import java.time.LocalDate

/**
 * 1. 현재 날짜
 * 2. 금일 데이터 유무
 * 3.
 */
class TodoWidgetHelper(
    val widget: RemoteViews
) {
    private val localDate: LocalDate = LocalDate.now()

    fun setTodayDate(): TodoWidgetHelper {
        widget.setTextViewText(R.id.widget_today_tv, localDate.toString())
        return TodoWidgetHelper(widget)
    }

    fun setTodayRoutine(context: Context?): TodoWidgetHelper {
        val serviceIntent = Intent(context, TodoRemoteViewService::class.java)
        widget.setRemoteAdapter(R.id.widget_lv, serviceIntent)
        return TodoWidgetHelper(widget)
    }

}
