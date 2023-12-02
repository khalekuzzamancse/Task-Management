package com.khalekuzzamanjustcse.taskmanagement

import android.app.Application
import com.khalekuzzamanjustcse.taskmanagement.data_layer.TaskTable
import com.khalekuzzamanjustcse.taskmanagement.notification.Notifier
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class BaseApplication : Application() {
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