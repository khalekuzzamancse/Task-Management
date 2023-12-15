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
import com.khalekuzzamanjustcse.taskmanagement.ui_layer.navigation.screens.CommonScreen

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


