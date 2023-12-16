package com.khalekuzzamanjustcse.taskmanagement.ui_layer.navigation.screens.my_taskes

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.khalekuzzamanjustcse.taskmanagement.data_layer.task_managment.TaskAssignedToMe
import com.khalekuzzamanjustcse.taskmanagement.ui_layer.navigation.screens.CommonScreen

@Preview
@Composable
fun TaskDetailsPreview() {
//    val task =
//        TaskEntity(
//            title = "Task 2",
//            description = "Description for Task 2",
//        )
//    TaskDetailsScreen(
//        task = task,
//        onClose = {}
//    )
}


@Composable
fun TaskToMeDetailsScreen(
    task: TaskAssignedToMe,
) {
        Column(
            modifier = Modifier
                .padding(start = 8.dp, end = 8.dp, bottom = 8.dp)
                .verticalScroll(rememberScrollState())
                .fillMaxSize()
        ) {
            Text(
                text = task.title,
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Assigned by : ${task.assignerName}",
                style = MaterialTheme.typography.labelSmall,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = task.dueDate,
                style = MaterialTheme.typography.labelSmall,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = task.description,
            )
        }

}


@Composable
fun TaskDetailsScreen(
    task: TaskAssignedToMe,
    onClose: () -> Unit,
) {

    CommonScreen(
        title = "Task Details",
        onBackArrowClick = onClose,
        false
    )
    { scaffoldPadding ->
        Column(
            modifier = Modifier
                .padding(scaffoldPadding)
                .padding(start = 8.dp, end = 8.dp, bottom = 8.dp)
                .verticalScroll(rememberScrollState())
                .fillMaxSize()
        ) {
            Text(
                text = task.title,
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Assigned by : ${task.assignerName}",
                style = MaterialTheme.typography.labelSmall,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = task.dueDate,
                style = MaterialTheme.typography.labelSmall,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = task.description,
            )
        }
    }
}
