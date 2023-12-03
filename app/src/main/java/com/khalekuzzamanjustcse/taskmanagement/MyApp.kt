package com.khalekuzzamanjustcse.taskmanagement

import android.app.Application
import android.content.ContentResolver
import android.util.Log
import com.khalekuzzamanjustcse.taskmanagement.data_layer.TaskTable
import com.khalekuzzamanjustcse.taskmanagement.notification.Notifier
import com.khalekuzzamanjustcse.taskmanagement.ui_layer.navigation.screens.device_contact.Contact
import com.khalekuzzamanjustcse.taskmanagement.ui_layer.navigation.screens.device_contact.FetchContact
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class BaseApplication : Application() {
    //don't hold unnecessary reference,when do not need then
    // make it eligible for garbage collection




    private fun notifyTasksAssigned() {
        var notificationId = 1
        CoroutineScope(Dispatchers.IO).launch {
            TaskTable().getTasks().collect { tasks ->
                tasks.forEach { task ->
                    val notNotified = !task.notified
                    if (notNotified) {
                        Notifier(this@BaseApplication)
                            .notify(
                                title = "New Notification",
                                message = "Hey task\n${task.title}\nby ${task.assignerName}",
                                notificationId = notificationId++,
                                taskId = task.id
                            )
                    }
                }
            }
        }

    }


    override fun onCreate() {
        super.onCreate()
        notifyTasksAssigned()

    }

}