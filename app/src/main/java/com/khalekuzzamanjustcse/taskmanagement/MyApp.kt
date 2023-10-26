package com.khalekuzzamanjustcse.taskmanagement

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.os.Build
import android.widget.Toast
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.platform.LocalContext
import com.khalekuzzamanjustcse.taskmanagement.data.TaskTable
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class BaseApplication : Application() {


    companion object {
        lateinit var notificationManager: NotificationManager

    }

    fun createNotification(
        message: String,
    ) {
        this.baseContext?.let {
            createNotification(it, message)
        }

    }

    private fun observeTaskNotification() {
        //show notification
       CoroutineScope(Dispatchers.IO).launch {
            TaskTable().getTasks().collect { takes ->
                takes.forEach {
                    if (!it.notified) {
                        createNotification(
                            message = "New task\n${it.title}\nby ${it.assignerName}"
                        )
                    }
                }
            }
        }

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
        notificationChannel.vibrationPattern = longArrayOf(100, 200, 300, 400, 300, 200, 100)

        notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(notificationChannel)

        observeTaskNotification()
    }
}