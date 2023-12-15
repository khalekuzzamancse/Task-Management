package com.khalekuzzamanjustcse.taskmanagement.data_layer.task_managment

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.tooling.preview.Preview
import com.khalekuzzamanjustcse.taskmanagement.data_layer.DatabaseCRUD
import com.khalekuzzamanjustcse.taskmanagement.data_layer.Updater


class TaskUpdater {
    suspend fun markAsComplete(
        myUserId: String,
        taskId: String
    ): Boolean {
        val task = DatabaseCRUD().read<Task>(
            collection = "NewTaks",
            docId = taskId
        )
        if (task != null) {
            val assignees = task.assignedUsers.map {
                if (it.userId == myUserId) it.copy(state = 3)
                else it
            }
            return DatabaseCRUD().update(
                collectionName = "NewTaks",
                updater = Updater(
                    docID = taskId,
                    field = "assignedUsers",
                    value = assignees
                )
            )

        } else return false

    }

}