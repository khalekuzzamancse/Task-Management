package com.khalekuzzamanjustcse.taskmanagement.notification

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.TaskStackBuilder
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.core.net.toUri
import com.khalekuzzamanjustcse.taskmanagement.DeepLink
import com.khalekuzzamanjustcse.taskmanagement.MainActivity
import com.khalekuzzamanjustcse.taskmanagement.R

//taking context as instance variable so after using class instance make it eligible for garbage collection
//by assigning null to it  instance or use other technique so that it references is not hold unnecessary
class Notifier(
    private val context: Context,
) {
    private val manager = context.getSystemService(Context.NOTIFICATION_SERVICE)
            as NotificationManager

    //notificationID required to send each notification individually,installed of replacing old one
    fun notify(
        title: String,
        message: String,
        notificationId: Int = 1,
        taskId: String,
    ) {
        //creating channel
        val channelID = "channel_1}"
        val channel = crateChannel(channelId = channelID, channelName = "Channel_01")
        attachChannelToManager(channel)
        //creating notification
        val pendingIntent = createPendingIntent(message)
        val deepLinkPendingIntent = deepLinkPendingIntent(taskId)

        //
        val notification = createNotification(
            channelId = channelID, title = title,
            message = message,
            pendingIntent =deepLinkPendingIntent?:pendingIntent
        )
        //sending notification
        manager.notify(notificationId, notification)
    }
    fun notify(
        title: String,
        message: String,
        notificationId: Int = 1,
    ) {
        //creating channel
        val channelID = "channel_1}"
        val channel = crateChannel(channelId = channelID, channelName = "Channel_01")
        attachChannelToManager(channel)
        //creating notification
        val pendingIntent = createPendingIntent(message)
        //
        val notification = createNotification(
            channelId = channelID, title = title,
            message = message,
            pendingIntent =pendingIntent
        )
        //sending notification
        manager.notify(notificationId, notification)
    }


    private fun crateChannel(
        channelId: String, channelName: String,
        description: String = "notification channel desc..",
        enableVibration: Boolean = true, enableLights: Boolean = true,
        vibrationPattern: LongArray = longArrayOf(100, 200, 300, 400, 300, 200, 100),
        importance: Int = NotificationManager.IMPORTANCE_HIGH,
    ): NotificationChannel {
        val channel = NotificationChannel(channelId, channelName, importance)
        channel.description = description
        channel.enableVibration(enableVibration)
        channel.enableLights(enableLights)
        channel.vibrationPattern = vibrationPattern
        return channel

    }

    private fun attachChannelToManager(channel: NotificationChannel) {
        manager.createNotificationChannel(channel)
    }


    private fun createNotification(
        title: String,
        channelId: String,
        message: String,
        pendingIntent: PendingIntent,
    ): Notification {
        return NotificationCompat.Builder(context, channelId)
            .setContentTitle(title)
            .setContentText(message)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setAutoCancel(true)
            .addAction(0, "Start", pendingIntent)
            .setContentIntent(pendingIntent)
            .build()
    }

    private fun createPendingIntent(
        message: String,
    ): PendingIntent {
        val flag = PendingIntent.FLAG_IMMUTABLE
        val intent = Intent(context, MainActivity::class.java).apply {
            putExtra("data", message)
        }
        return PendingIntent.getActivity(context, 100, intent, flag)
    }

     private fun deepLinkPendingIntent(
         taskId:String
     ): PendingIntent? {
        val deepLinkIntent = Intent(
            Intent.ACTION_VIEW,
            "${DeepLink.DEEP_LINK_URL}/$taskId".toUri(),
            context,
            MainActivity::class.java
        )
        val deepLinkPendingIntent: PendingIntent? = TaskStackBuilder.create(context).run {
            addNextIntentWithParentStack(deepLinkIntent)
            getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT)
        }
        return deepLinkPendingIntent
    }


}

class NotificationFactory(
    private val context: Context
) {
    suspend fun observeFriendRequest() {
//        val request = FriendManager().getReceivedFriendRequest()
//        request.forEach {
//            if (!it.hasNotified) {
//                Notifier(context)
//                    .notify(
//                        title = "New Notification",
//                        message = "Friend request",
//                        taskId = "123"
//                    )
//                //  createNotification(context, "${it.user.name} send a friend request.")
//            }
//        }
    }

}




