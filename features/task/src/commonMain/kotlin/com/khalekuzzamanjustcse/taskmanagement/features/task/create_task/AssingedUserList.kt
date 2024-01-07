package com.khalekuzzamanjustcse.taskmanagement.features.task.create_task

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

data class TaskAssignedUser(
    val name: String,
    val phone: String,
    val selected: Boolean,
)


@Composable
fun AssignUser(
    modifier: Modifier = Modifier,
    users: List<TaskAssignedUser> = emptyList(),
    onUserChosen: (Int) -> Unit = {},
    onUserChooseFinished: () -> Unit = {},
) {
    Scaffold(
        floatingActionButton = {
            Button(onClick = {
                onUserChooseFinished()
            }) {
                Text(text = "Done")
            }
        }
    ) { scaffoldPadding ->
        Column(modifier = modifier.padding(scaffoldPadding)) {
            users.forEachIndexed { i, it ->
                UserInfoCard(
                    name = it.name,
                    phone = it.phone,
                    selected = it.selected,
                    onUserChosen = { onUserChosen(i) },
                    savedInContact = false
                )
            }
        }

    }



}
