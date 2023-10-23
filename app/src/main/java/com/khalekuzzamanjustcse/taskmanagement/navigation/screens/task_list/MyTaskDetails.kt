package com.khalekuzzamanjustcse.taskmanagement.navigation.screens.task_list

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.khalekuzzamanjustcse.taskmanagement.ui.components.MyTask

@Preview
@Composable
fun TaskDetailsPreview() {
   val task= MyTask("Task 2", "Description for Task 2", "Jane Smith", false, "2023-10-24 10:30 AM")
    TaskDetails(
        title =task.tile,
        assigner =task.assigner,
        timeStamp =task.timestamp,
        description =task.description,
        onClose = {}
    )
}

@Composable
fun TaskDetails(
    title: String,
    assigner: String,
    timeStamp: String,
    description: String,
    onClose: () -> Unit,
) {
    Column(modifier = Modifier
        .padding(8.dp)
        .fillMaxSize()
    ) {
        IconButton(onClick = onClose) {
            Icon(
                Icons.Filled.Cancel, null
            )
        }
        Text(
            text = title,
            style = MaterialTheme.typography.titleLarge,
        )
        Text(
            text = "Assigned by : $assigner",
            style = MaterialTheme.typography.labelSmall,
        )
        Text(
            text = timeStamp,
            style = MaterialTheme.typography.labelSmall,
        )
        Text(
            text = description,
            style = MaterialTheme.typography.titleMedium,
        )
    }
}