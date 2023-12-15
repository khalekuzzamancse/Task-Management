package com.khalekuzzamanjustcse.taskmanagement

import android.app.Application
import android.util.Log
import com.khalekuzzamanjustcse.taskmanagement.data_layer.AuthManager
import com.khalekuzzamanjustcse.taskmanagement.data_layer.notification.AssignedToMeTasksObserver
import com.khalekuzzamanjustcse.taskmanagement.data_layer.notification.ObservableFriendShip
import com.khalekuzzamanjustcse.taskmanagement.notification.Notifier
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

    }


    private fun log(message: String) {
        Log.d(TAG, message)
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        AuthManager//initialize
        val scope = CoroutineScope(Dispatchers.IO)
//        scope.launch {
//           ObservableFriendShip._friendShipWithMe.collect {
//                Log.d("friendShipStatus:App", "${it}")
//            }
//
//        }
        scope.launch {
            AuthManager.signedInUserPhone()?.let {
                ObservableFriendShip.subscribe(it)

            }

        }
        scope.launch {
            AuthManager.signedInUserPhone()?.let {
                Log.d("MyAssignedTask:App", "$it")
                AssignedToMeTasksObserver.subscribe(it)
            }

        }

    }

}