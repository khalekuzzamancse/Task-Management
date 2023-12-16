package com.khalekuzzamanjustcse.taskmanagement.ui_layer.navigation.screens.my_taskes

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.khalekuzzamanjustcse.taskmanagement.data_layer.task_managment.TaskAssignedToMe
import com.khalekuzzamanjustcse.taskmanagement.ui_layer.components.MyTaskCard
import com.khalekuzzamanjustcse.taskmanagement.ui_layer.components.generic_screen.GenericListComposable


@Composable
fun TasksAssignedToMeList(
    modifier: Modifier=Modifier,
    tasks:List<TaskAssignedToMe>,
    onTaskDetailsRequest: (TaskAssignedToMe)->Unit,
    onTaskCompleteRequest: (TaskAssignedToMe,Boolean)->Unit,
) {

    GenericListComposable(
        modifier = modifier,
        items = tasks,
        itemContent = { myTask ->
            MyTaskCard(
                title = myTask.title,
                assigner = myTask.assignerName,
                onCheckedChanged = {
                    onTaskCompleteRequest(myTask, it)
                },
                checked = myTask.complete,
                onLongClick = {
                    onTaskDetailsRequest(myTask)
                }
            )
        }
    )



}

@Composable
fun TasksAssignedToMeScreen(
    items:List<TaskAssignedToMe>,
    onTaskClick: (TaskAssignedToMe)->Unit={}
) {


        LazyColumn {
            items(items) { myTask ->
                MyTaskCard(
                    title = myTask.title,
                    assigner = myTask.assignerName,
                    onCheckedChanged = {
                       // viewModel.onCheckChanged(myTask, it)
                    },
                    checked = myTask.complete,
                    onLongClick = {
                        onTaskClick(myTask)
                    }
                )
            }
        }


}

