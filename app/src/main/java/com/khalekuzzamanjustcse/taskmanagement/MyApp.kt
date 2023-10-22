package com.khalekuzzamanjustcse.taskmanagement

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.os.Build
import android.widget.Toast

class BaseApplication : Application() {
    private lateinit var networkCallback: ConnectivityManager.NetworkCallback
    companion object{
        lateinit var notificationManager: NotificationManager
    }

    override fun onCreate() {
        super.onCreate()

        val notificationChannel = NotificationChannel(
            "channel_id",
            "channel_name",
            NotificationManager.IMPORTANCE_HIGH
        )
        notificationChannel.description = "notification channel desc.."
        notificationChannel.enableVibration(true)
        notificationChannel.enableLights(true)
        notificationChannel.vibrationPattern = longArrayOf(100,200,300,400,300,200,100)

        notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(notificationChannel)


    }
}