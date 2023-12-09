package com.example.androidassignment.services

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.androidassignment.R

object NotificationService {

    private const val CHANNEL_ID = "42"
    private const val NOTIFICATION_ID = 2

    @SuppressLint("MissingPermission")
    fun showNotification(context: Context){
        val builder = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle(context.getString(R.string.lbl_notification_title))
            .setContentText(context.getString(R.string.lbl_notification_description))
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        createNotificationChannel(context)

        with(NotificationManagerCompat.from(context)) {
            notify(NOTIFICATION_ID, builder.build())
        }
    }

    private fun createNotificationChannel(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = context.getString(R.string.lbl_channel_name)
            val descriptionText = context.getString(R.string.lbl_channel_description)
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }
            val notificationManager: NotificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }
}