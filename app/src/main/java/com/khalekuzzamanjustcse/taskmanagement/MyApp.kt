package com.khalekuzzamanjustcse.taskmanagement

import android.app.Application
import android.util.Log
import com.khalekuzzamanjustcse.taskmanagement.data_layer.AuthManager
import com.khalekuzzamanjustcse.taskmanagement.data_layer.FriendShipManager
import com.khalekuzzamanjustcse.taskmanagement.data_layer.TaskTable
import com.khalekuzzamanjustcse.taskmanagement.data_layer.TaskTable2
import com.khalekuzzamanjustcse.taskmanagement.notification.Notifier
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class BaseApplication : Application() {
    //don't hold unnecessary reference,when do not need then
    // make it eligible for garbage collection

    companion object {
        private const val TAG = "MyAppLog: "
    }

    private fun log(message: String) {
        Log.d(TAG, message)
    }

    private fun notifyTasksCompleted() {
        var notificationId = 1
        CoroutineScope(Dispatchers.IO).launch {
            val taskTable = TaskTable2("017388")
            taskTable.getCompletedNotNotifiedTask().forEach { task ->
                Notifier(this@BaseApplication)
                    .notify(
                        title = "Task Completeded",
                        message = "${task.title}\nby ${task.assignerPhone}",
                        notificationId = notificationId++,
                        taskId = task.taskAssignedId
                    )
                taskTable.makeTaskCompletedTaskNotified(task.taskAssignedId)
            }
        }

    }
    private fun notifyTasksAssigned() {
        var notificationId = 1
        CoroutineScope(Dispatchers.IO).launch {
            val taskTable = TaskTable2("0123")
                taskTable.getAssignedNotNotifiedTask().forEach { task ->
                Notifier(this@BaseApplication)
                    .notify(
                        title = "New Task",
                        message = "${task.title}\nby ${task.assignerPhone}",
                        notificationId = notificationId++,
                        taskId = task.taskAssignedId
                    )
                    taskTable.makeAssignedTaskNotified(task.taskAssignedId)
            }
        }

    }

    private fun notifyAcceptFriendRequest() {
        val friendShipManager = FriendShipManager()
        var notificationId = 1
        CoroutineScope(Dispatchers.IO).launch {
            val signedUserPhone = AuthManager().signedInUserPhone()
            if (signedUserPhone != null) {
                val accepted = friendShipManager.getAcceptNotNotifiedRequest(signedUserPhone)
                log("$accepted")
                accepted.forEach { friend ->
                    Notifier(this@BaseApplication)
                        .notify(
                            title = "Friend Request Accepted",
                            message = "${friend.name} accepted request",
                            notificationId = notificationId++,
                        )
                    friendShipManager.acceptRequestNotified(friend.friendShipId)
                }
            }

        }
    }

    private fun notifyIncomingFriendRequest() {
        val friendShipManager = FriendShipManager()
        var notificationId = 1
        CoroutineScope(Dispatchers.IO).launch {
            val signedUserPhone = AuthManager().signedInUserPhone()
            if (signedUserPhone != null) {
                val request = friendShipManager.getUnNotifiedFriendRequest(signedUserPhone)
                log("$request")
                request.forEach { friend ->
                    log(friend.friendShipId)
                    Notifier(this@BaseApplication)
                        .notify(
                            title = "New Friend Request",
                            message = "${friend.name} send request",
                            notificationId = notificationId++,
                        )
                    friendShipManager.makeRequestNotified(friend.friendShipId)

                }
            }

        }
    }


    override fun onCreate() {
        super.onCreate()
        notifyTasksAssigned()
        notifyAcceptFriendRequest()
        notifyIncomingFriendRequest()

    }

}