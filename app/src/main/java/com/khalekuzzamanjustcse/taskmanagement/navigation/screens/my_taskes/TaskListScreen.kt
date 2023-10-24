package com.khalekuzzamanjustcse.taskmanagement.navigation.screens.my_taskes

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import com.khalekuzzamanjustcse.taskmanagement.ui.components.GenericListScreen
import com.khalekuzzamanjustcse.taskmanagement.ui.components.MyTaskCard
import com.khalekuzzamanjustcse.taskmanagement.ui.components.MyTask

val dummyTaskList = listOf(
    MyTask(
        "Solve the Tree and Graph theory problems.",
        "You have to solve all the problems of Tree and Graph theory.\n" +
                "Follow the reference books and the IIT Delhi videos." ,
        "Mr Bean",
        false,
        "23 OCT 2023"
    ),
    MyTask("Task 2", "Description for Task 2", "Jane Smith", false, "2023-10-24 10:30 AM"),
    MyTask(
        "Task 3",
        "Description for Task 3",
        "Alice Johnson",
        true,
        "2023-10-25 03:15 PM"
    ),
    MyTask(
        "Task 4",
        "Description for Task 4",
        "Bob Williams",
        false,
        "2023-10-26 11:45 AM"
    ),
    MyTask("Task 5", "Description for Task 5", "Eva Brown", true, "2023-10-27 09:30 AM")
)


@Composable
fun MyTaskListScreen(
    viewModel:MyTaskViewModel,
) {

    val currentOpenTask=viewModel.currentOpenTask.collectAsState().value

    if(currentOpenTask!=null){
        TaskDetails(
            title =currentOpenTask.tile,
            assigner =currentOpenTask.assigner,
            timeStamp =currentOpenTask.timestamp,
            description =currentOpenTask.description,
            onClose = viewModel::onTaskDescriptionClose
        )
    }
    else{
        GenericListScreen(
            items = viewModel.tasks.collectAsState().value,
            isLoading = viewModel.isLoading.collectAsState().value,
            itemContent = { myTask ->
                MyTaskCard(
                    title = myTask.tile,
                    assigner = myTask.assigner,
                    onCheckedChanged = {
                        viewModel.onCheckChanged(myTask, it)
                    },
                    checked = myTask.status,
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