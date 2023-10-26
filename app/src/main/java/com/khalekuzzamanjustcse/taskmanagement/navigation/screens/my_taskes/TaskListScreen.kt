package com.khalekuzzamanjustcse.taskmanagement.navigation.screens.my_taskes

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.platform.LocalContext
import com.khalekuzzamanjustcse.taskmanagement.createNotification
import com.khalekuzzamanjustcse.taskmanagement.ui.components.GenericListScreen
import com.khalekuzzamanjustcse.taskmanagement.ui.components.MyTaskCard
import com.khalekuzzamanjustcse.taskmanagement.ui.components.MyTask


@Composable
fun MyTaskListScreen(
    viewModel: MyTaskViewModel,
) {
    val currentOpenTask = viewModel.currentOpenTask.collectAsState().value
    if (currentOpenTask != null) {
        TaskDetails(
            title = currentOpenTask.title,
            assigner = currentOpenTask.assignerName,
            timeStamp = "23 June 2023",
            description = currentOpenTask.description,
            onClose = viewModel::onTaskDescriptionClose
        )
    } else {
        GenericListScreen(
            items = viewModel.tasks.collectAsState().value,
            isLoading = viewModel.isLoading.collectAsState().value,
            itemContent = { myTask ->
                MyTaskCard(
                    title = myTask.title,
                    assigner = myTask.assignerName,
                    onCheckedChanged = {
                        viewModel.onCheckChanged(myTask, it)
                    },
                    checked = myTask.complete,
                    onLongClick = {
                        viewModel.onLongClick(myTask)
                    }
                )
            },
            screenTitle = "My Task",
            onBack = { }
        )

    }

}