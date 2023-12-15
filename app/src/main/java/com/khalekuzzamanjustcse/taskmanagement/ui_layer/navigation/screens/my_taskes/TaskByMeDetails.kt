package com.khalekuzzamanjustcse.taskmanagement.ui_layer.navigation.screens.my_taskes

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.khalekuzzamanjustcse.taskmanagement.ui_layer.navigation.screens.CommonScreen



@Composable
fun TaskAssignedByMeDetails(
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
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Assignees",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.align(Alignment.CenterHorizontally)
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
