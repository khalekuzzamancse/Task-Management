package com.khalekuzzamanjustcse.taskmanagement

import android.app.Application
import android.util.Log
import com.khalekuzzamanjustcse.taskmanagement.data_layer.AuthManager
import com.khalekuzzamanjustcse.taskmanagement.data_layer.task_managment.AssignedToMeTasksObserver
import com.khalekuzzamanjustcse.taskmanagement.data_layer.friend_management.FriendShipObserver
import com.khalekuzzamanjustcse.taskmanagement.data_layer.task_managment.AssignedByMeTasksObserver
import com.khalekuzzamanjustcse.taskmanagement.data_layer.user_managment.UsersObserver
import com.khalekuzzamanjustcse.taskmanagement.notification.Notifier
import com.khalekuzzamanjustcse.taskmanagement.ui_layer.navigation.screens.device_contact.LocalContactsProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class BaseApplication : Application() {
    //don't hold unnecessary reference,when do not need then
    // make it eligible for garbage collection

    companion object {
        private const val TAG = "MyAppLog: "
        private lateinit var instance: BaseApplication
        private var notificationId = 1

        fun notify(title: String, message: String) {
            val notifier = Notifier(instance.applicationContext)
            notifier.notify(
                title = title,
                message = message,
                notificationId = notificationId++,
            )
        }
        fun getLocalContact()=
            LocalContactsProvider(instance.applicationContext.contentResolver)
                .getContact()

    }


    private fun log(message: String) {
        Log.d(TAG, message)
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        AuthManager//initialize
        val scope = CoroutineScope(Dispatchers.IO)
        scope.launch {
            AuthManager.signedInUserPhone()?.let {
                FriendShipObserver.subscribe(it)
            }

        }
        scope.launch {
            AuthManager.signedInUserPhone()?.let {
                AssignedToMeTasksObserver.subscribe(it)
            }

        }
        scope.launch {
            AuthManager.signedInUserPhone()?.let {
                UsersObserver.subscribe(it)
            }
        }
        scope.launch {
            AuthManager.signedInUserPhone()?.let {
                AssignedByMeTasksObserver.subscribe(it)
            }
        }

    }

}