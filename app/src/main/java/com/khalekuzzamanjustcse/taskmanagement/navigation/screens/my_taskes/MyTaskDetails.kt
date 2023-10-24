package com.khalekuzzamanjustcse.taskmanagement.navigation.screens.my_taskes

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.khalekuzzamanjustcse.taskmanagement.navigation.screens.CommonScreen
import com.khalekuzzamanjustcse.taskmanagement.ui.components.MyTask

@Preview
@Composable
fun TaskDetailsPreview() {
    val task =
        MyTask("Task 2", "Description for Task 2", "Jane Smith", false, "2023-10-24 10:30 AM")
    TaskDetails(
        title = task.tile,
        assigner = task.assigner,
        timeStamp = task.timestamp,
        description = task.description,
        onClose = {}
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskDetails(
    title: String,
    assigner: String,
    timeStamp: String,
    description: String,
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
                .fillMaxSize()
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Assigned by : $assigner",
                style = MaterialTheme.typography.labelSmall,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = timeStamp,
                style = MaterialTheme.typography.labelSmall,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = description,
            )
        }
    }
}
