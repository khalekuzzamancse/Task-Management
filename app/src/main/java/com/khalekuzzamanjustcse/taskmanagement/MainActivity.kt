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
                LaunchedEffect(Unit){
                    NotificationFactory(context).observeFriendRequest()
                }
                NavGraph()

            }

        }

    }
}
