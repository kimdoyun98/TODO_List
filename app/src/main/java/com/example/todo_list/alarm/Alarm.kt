package com.example.todo_list.alarm

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import java.util.Calendar
import java.util.Date

class Alarm(
    private val context: Context
) {
    companion object{
        const val ALARM_REQUEST_CODE = "alarm_rqCode"
        const val CONTENT = "content"
        const val CHECKED_DAY_LIST = "checkedDayList"
        const val HOUR = "hour"
        const val MINUTE = "minute"
    }

    private val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as? AlarmManager

    fun setAlarm(hour : Int, minute : Int, alarm_code : Int, content : String, checkedDayList:MutableList<Boolean>){
        val intent = Intent(context, AlarmReceiver::class.java).apply {
            putExtra(ALARM_REQUEST_CODE, alarm_code)
            putExtra(CONTENT, content)
            putExtra(CHECKED_DAY_LIST, checkedDayList.toBooleanArray())
            putExtra(HOUR, hour)
            putExtra(MINUTE, minute)
        }

        val pendingIntent = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S){
            PendingIntent.getBroadcast(context,alarm_code,intent,PendingIntent.FLAG_IMMUTABLE)
        }else{
            PendingIntent.getBroadcast(context,alarm_code,intent,PendingIntent.FLAG_UPDATE_CURRENT)
        }

        val calendar : Calendar = Calendar.getInstance().apply {
            timeInMillis = System.currentTimeMillis()
            set(Calendar.HOUR_OF_DAY, hour)
            set(Calendar.MINUTE, minute)
            set(Calendar.SECOND, 0)
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val alarmClock = AlarmManager.AlarmClockInfo(calendar.timeInMillis, pendingIntent)
            alarmManager?.setAlarmClock(alarmClock, pendingIntent)
        }

    }

    fun cancelAlarm(alarm_code : Int){
        val intent = Intent(context, AlarmReceiver::class.java)
        val pendingIntent = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S){
            PendingIntent.getBroadcast(context,alarm_code,intent,PendingIntent.FLAG_IMMUTABLE)
        }else{
            PendingIntent.getBroadcast(context,alarm_code,intent,PendingIntent.FLAG_UPDATE_CURRENT)
        }
        alarmManager?.cancel(pendingIntent)
    }

}
