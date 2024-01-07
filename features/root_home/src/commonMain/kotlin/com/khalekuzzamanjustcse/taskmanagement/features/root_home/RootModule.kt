package com.khalekuzzamanjustcse.taskmanagement.features.root_home

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import com.khalekuzzamanjustcse.taskmanagement.features.task.create_task.CreateTaskScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RootModule() {
    CreateTaskScreen()

}