package com.khalekuzzamanjustcse.taskmanagement.data_layer.notification

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.tooling.preview.Preview
import com.khalekuzzamanjustcse.taskmanagement.BaseApplication
import com.khalekuzzamanjustcse.taskmanagement.data_layer.AuthManager
import com.khalekuzzamanjustcse.taskmanagement.data_layer.DatabaseCollectionReader
import com.khalekuzzamanjustcse.taskmanagement.data_layer.TaskEntity
import com.khalekuzzamanjustcse.taskmanagement.data_layer.UserCollection
import com.khalekuzzamanjustcse.taskmanagement.ui_layer.navigation.screens.my_taskes.TaskDoer
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch


@Preview
@Composable
fun Prev() {
    LaunchedEffect(Unit) {
        AssignedToMeTasksObserver.observeAssignedTask("01625337883")
    }
}

object AssignedToMeTasksObserver {
    private const val TASKS_COLLECTION = "NewTaks"
    private const val FIELD_ASSIGNER_ID = "assignerId"
    private const val STATE_CREATED_NOT_NOTIFIED = 1
    private const val STATE_CREATED_NOTIFIED = 2
    private const val STATE_COMPLETED_NOT_NOTIFIED = 3
    private const val STATE_COMPLETED_NOTIFIED = 4

    private val _taskEntities = MutableStateFlow(emptyList<Task>())
    private val _taskToMe = MutableStateFlow(emptyList<TaskEntity>())
    val taskToMe = _taskToMe.asStateFlow()

    init {
        val scope = CoroutineScope(Dispatchers.Main)
        scope.launch {
            AuthManager.signedInUserPhone()?.let {
                observe(it)
            }
        }
    }

    init {
        val scope = CoroutineScope(Dispatchers.Main)
        scope.launch {
            _taskEntities.collect { tasks ->
                _taskToMe.value = tasks.map { task ->
                    val res = TaskEntity(
                        title = task.title,
                        description = task.description,
                        dueDate = task.dueTime,
                        assignerName = task.assignerId,
                        assigneePhone = "11"
                    )
                    notifyNewTasks(task)
                    res
                    //  Log.d("MyAssignedTask:ToMe", "$task")

                }

            }
        }


    }

    private fun notifyNewTasks(task: Task) {
        task.assignedUsers.map {
            if (it.state == 1) {
                BaseApplication.notify(
                    title = "New Task",
                    message = task.title
                )
            }
        }
    }


    private suspend fun taskDoer(assignee: AssignedUser): TaskDoer? {
        val state = assignee.state
        val user = UserCollection().getUser(assignee.userId)
        return if (user != null) {
            TaskDoer(
                name = user.name,
                phone = user.phone,
                status = decodeTaskState(state)
            )
        } else null
    }

    private fun decodeTaskState(state: Int): String {
        return when (state) {
            1 -> "Unseen"
            2 -> "Seen"
            else -> "Completed"
        }
    }


    suspend fun observeAssignedTask(signedInUserId: String) {
        observe(signedInUserId)
    }


    private suspend fun observe(signedInUserId: String) {
        DatabaseCollectionReader(collection = TASKS_COLLECTION)
            .readObservable(Task::class.java)
            .collect { tasks ->
                _taskEntities.value =
                    tasks.filter { isTaskAssignedToMe(signedInUserId, it.assignedUsers) }
            }
    }

    private fun isTaskAssignedToMe(myUserId: String, assignees: List<AssignedUser>): Boolean {
        return assignees.any { it.userId == myUserId }
    }


}
