package com.khalekuzzamanjustcse.taskmanagement.ui_layer.navigation.screens.home

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import com.khalekuzzamanjustcse.taskmanagement.data_layer.TaskEntity
import com.khalekuzzamanjustcse.taskmanagement.ui_layer.components.MyTaskCard
import com.khalekuzzamanjustcse.taskmanagement.ui_layer.navigation.screens.my_taskes.MyTaskViewModel

@Composable
fun TasksAssignedToMeScreen(
    viewModel: MyTaskViewModel,
     items:List<TaskEntity>,
    onTaskClick: (TaskEntity)->Unit={}
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

