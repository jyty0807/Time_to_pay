package com.cookandroid.timetopay


import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.app.PendingIntent
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat

class AlarmReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        val notificationIntent = Intent(context, Alram::class.java)
        val pendingIntent = PendingIntent.getActivity(context, 0, notificationIntent, 0)

        val notification = NotificationCompat.Builder(context, "YOUR_CHANNEL_ID")
            .setContentTitle("알림 제목")
            .setContentText("오늘 일을 다 마치셨나요?")
            .setSmallIcon(R.drawable.logo)
            .setContentIntent(pendingIntent)
            .addAction(0, "예", pendingIntent) // '예' 텍스트 버튼
            .addAction(0, "아니오", null) // '아니오' 텍스트 버튼
            .setAutoCancel(true)
            .build()

        with(NotificationManagerCompat.from(context)) {
            notify(NOTIFICATION_ID, notification)
        }
    }

    companion object {
        private const val NOTIFICATION_ID = 1
    }
}