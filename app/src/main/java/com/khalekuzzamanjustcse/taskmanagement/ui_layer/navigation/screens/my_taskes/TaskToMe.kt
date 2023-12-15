package com.khalekuzzamanjustcse.taskmanagement.ui_layer.navigation.screens.my_taskes

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import com.khalekuzzamanjustcse.taskmanagement.data_layer.task_managment.TaskAssignedToMe
import com.khalekuzzamanjustcse.taskmanagement.ui_layer.components.MyTaskCard

@Composable
fun TasksAssignedToMeScreen(
    viewModel: MyTaskViewModel,
    items:List<TaskAssignedToMe>,
    onTaskClick: (TaskAssignedToMe)->Unit={}
) {


        LazyColumn {
            items(items) { myTask ->
                MyTaskCard(
                    title = myTask.title,
                    assigner = myTask.assignerName,
                    onCheckedChanged = {
                        viewModel.onCheckChanged(myTask, it)
                    },
                    checked = myTask.complete,
                    onLongClick = {
                        onTaskClick(myTask)
                    }
                )
            }
        }


}

