package com.khalekuzzamanjustcse.taskmanagement

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import com.khalekuzzamanjustcse.taskmanagement.navigation.navgraph.NavGraph
import com.khalekuzzamanjustcse.taskmanagement.ui.theme.TaskManagementTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TaskManagementTheme {
                val context = LocalContext.current
                LaunchedEffect(Unit) {
                    NotificationFactory(context).observeFriendRequest()
                }
                PermissionManage(
                    permissions = listOf(
                        "android.permission.READ_CONTACTS",
                        "android.permission.ACCESS_NETWORK_STATE",
                        "android.permission.FOREGROUND_SERVICE",
                        "android.permission.POST_NOTIFICATIONS"
                    )
                )
                NavGraph()

            }

        }

    }
}
