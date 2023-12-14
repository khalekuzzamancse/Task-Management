package com.khalekuzzamanjustcse.taskmanagement.ui_layer.navigation.screens.my_taskes

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.khalekuzzamanjustcse.taskmanagement.data_layer.AuthManager
import com.khalekuzzamanjustcse.taskmanagement.data_layer.TaskTable2
import com.khalekuzzamanjustcse.taskmanagement.ui_layer.navigation.screens.CommonScreen
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class TaskDoer(
    val name: String,
    val phone: String,
    val status: String,
)

data class TaskOwnedByMe(
    val taskId: String,
    val title: String,
    val description: String,
    val dueDate: String,
    val doers: List<TaskDoer>
)



@Composable
fun TasksOwnedByMe(
    tasks: List<TaskOwnedByMe>,
    onOpenDetails: (TaskOwnedByMe) -> Unit = {}
) {
    LazyColumn {
        items(tasks) { task ->
            MyOwnedTask(
                title = task.title,
                dueDate = task.dueDate,
                onClick = { onOpenDetails(task) }
            )
        }
    }
}

@Composable
fun MyOwnedTask(
    modifier: Modifier = Modifier,
    title: String,
    dueDate: String,
    onClick: () -> Unit = { },
) {
    Surface(
        modifier = modifier
            .padding(4.dp)
            .fillMaxWidth()
            .clickable {
                onClick()
            },
        shape = MaterialTheme.shapes.medium,
    ) {
        Column(
            modifier = Modifier,
           // horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
            )
            Text(
                text = dueDate,
                style = MaterialTheme.typography.labelSmall,
            )
        }

    }

}

@Composable
fun MyOwnedTaskDetailsScreen(
    task: TaskOwnedByMe,
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
                text = "Due Date :${task.dueDate}",
                style = MaterialTheme.typography.labelSmall,
                // modifier = Modifier.align(Alignment.CenterHorizontally)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = task.description,
            )
            task.doers.forEach {
                TaskDoerInfo(
                    modifier = Modifier
                        .fillMaxWidth(),
                    doer = it
                )

            }


        }
    }
}

@Composable
fun TaskDoerInfo(
    modifier: Modifier,
    doer: TaskDoer
) {
    Column(modifier = modifier) {
        Text(
            text = "Assignees",
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
        Text(
            text = "${doer.name}(${doer.phone})",
            style = MaterialTheme.typography.titleSmall,
            //  modifier = Modifier.align(Alignment.CenterHorizontally)
        )
        Text(
            text = "Status: ${doer.status}",
            style = MaterialTheme.typography.labelSmall,
            modifier = Modifier.padding(start = 4.dp)
            //.align(Alignment.CenterHorizontally)
        )
        Spacer(modifier = Modifier.height(4.dp))
    }
}


