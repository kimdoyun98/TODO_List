package com.example.todo_list.alarm

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import com.example.todo_list.R
import com.example.todo_list.alarm.Alarm.Companion.ALARM_REQUEST_CODE
import com.example.todo_list.alarm.Alarm.Companion.CONTENT
import com.example.todo_list.ui.MainActivity
import com.example.todo_list.util.MyApplication

class AlarmReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent?) {
        if (!MyApplication.prefs.getAlarm()) return

        val requestCode = intent?.extras!!.getInt(ALARM_REQUEST_CODE)
        val content = intent.extras!!.getString(CONTENT)

        val notificationManager: NotificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        createChannel(notificationManager)

        val pendingIntent = createPendingIntent(context, requestCode)
        val builder = createNotificationBuilder(context, content, pendingIntent)

        notificationManager.notify(1, builder)
    }

    private fun createNotificationBuilder(
        context: Context,
        content: String?,
        pendingIntent: PendingIntent
    ): Notification {
        return NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.mipmap.todo_icon)
            .setContentTitle(context.getString(R.string.app_name))
            .setContentText(content)
            .setAutoCancel(true)
            .setShowWhen(true)
            .setColor(ContextCompat.getColor(context, R.color.purple_200))
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(pendingIntent)
            .build()
    }

    private fun createChannel(notificationManager: NotificationManager) {
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        notificationManager.createNotificationChannel(
            NotificationChannel(CHANNEL_ID, CHANNEL_NAME, importance)
        )
    }

    private fun createPendingIntent(context: Context, requestCode: Int): PendingIntent {
        val intent = Intent(context, MainActivity::class.java)

        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S)
            PendingIntent.getActivity(
                context,
                requestCode,
                intent,
                PendingIntent.FLAG_IMMUTABLE
            )
        else PendingIntent.getActivity(
            context,
            requestCode,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )
    }

    companion object {
        private const val CHANNEL_ID = "TodayAlarm"
        private const val CHANNEL_NAME = "Alarm"
    }
}
