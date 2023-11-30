package com.khalekuzzamanjustcse.taskmanagement.ui_layer.navigation.screens.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.khalekuzzamanjustcse.taskmanagement.data_layer.TaskEntity
import com.khalekuzzamanjustcse.taskmanagement.ui_layer.components.MyTaskCard
import com.khalekuzzamanjustcse.taskmanagement.ui_layer.navigation.screens.my_taskes.MyTaskViewModel

@Composable
fun MyTaskList(
    viewModel: MyTaskViewModel,
    onTaskClick: (TaskEntity)->Unit={}
) {
    val items= viewModel.tasks.collectAsState().value
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

@Composable
fun MyTaskDetails(
    modifier: Modifier = Modifier,
    task: TaskEntity
) {
    Column(
        modifier = modifier
    ) {
        Text(
            text =task. title,
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
            text = "5 OCTOBER 2023",
            style = MaterialTheme.typography.labelSmall,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text =task.description,
        )
    }
}
