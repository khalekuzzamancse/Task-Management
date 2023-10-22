package com.khalekuzzamanjustcse.taskmanagement



import android.Manifest
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.app.NotificationCompat



@Composable
fun ShowNotificationScreen() {
    val context = LocalContext.current
    val notificationLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) {

    }

    LaunchedEffect(key1 = true) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            notificationLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
        }
    }

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Button(onClick = {
            createNotification(context)
        }) {
            Text(text = "Show Notification")
        }
    }

}

 fun createNotification(
    context: Context,
    message:String="The App is running",
) {
    val notificationManager = BaseApplication.notificationManager

    val notification = NotificationCompat.Builder(context, "channel_id")
        .setContentTitle("Notification Title")
        .setContentText(message)
        .setSmallIcon(R.drawable.ic_launcher_foreground)
        .setAutoCancel(true)
        .addAction(0, "Start", createPendingIntent(context,message))
        .setContentIntent(createPendingIntent(context,message))
        .build()

    notificationManager.notify(100, notification)
}

private fun createPendingIntent(
    context: Context,
    message:String,
): PendingIntent {

    val flag = PendingIntent.FLAG_IMMUTABLE

    val intent = Intent(context, MainActivity::class.java).apply {
        putExtra("data", message)
    }

    return PendingIntent.getActivity(context, 100, intent, flag)

}












