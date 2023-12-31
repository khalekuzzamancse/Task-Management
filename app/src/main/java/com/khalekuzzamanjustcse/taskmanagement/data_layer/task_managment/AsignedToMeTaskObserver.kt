package com.khalekuzzamanjustcse.taskmanagement.data_layer.task_managment

import com.khalekuzzamanjustcse.taskmanagement.BaseApplication
import com.khalekuzzamanjustcse.taskmanagement.data_layer.DatabaseCollectionReader
import com.khalekuzzamanjustcse.taskmanagement.data_layer.DatabaseDocumentReader
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch

data class TaskAssignedToMe(
    val taskId: String,
    val title: String,
    val description: String,
    val assignerName: String,
    val assigneePhone: String,
    val dueDate: String,
    val complete: Boolean = false
)


object AssignedToMeTasksObserver {
    private const val TASKS_COLLECTION = "NewTaks"
    private const val STATE_CREATED_NOT_NOTIFIED = 1
    // private const val STATE_COMPLETED_NOT_NOTIFIED = 3

    private val _taskEntities = MutableStateFlow(emptyList<Task>())
    private val _taskToMe = MutableStateFlow(emptyList<TaskAssignedToMe>())
    val taskToMe = _taskToMe.asStateFlow()


    suspend fun taskDetails(taskId: String): Flow<TaskAssignedToMe> = flow {
        DatabaseDocumentReader(
            collection = TASKS_COLLECTION,
            docId = taskId
        ).readObservable(Task::class.java).collect { task ->
            val taskToMe = TaskAssignedToMe(
                taskId = task.taskId,
                title = task.title,
                description = task.description,
                dueDate = task.dueTime,
                assignerName = task.assignerId,
                assigneePhone = task.assignerId
            )
            emit(taskToMe)
        }
    }

    private fun onUpdateTaskEntities(myUserId: String, tasks: List<Task>) {
        _taskEntities.value = tasks
        updateTaskToMe(myUserId, tasks)
    }

    private fun updateTaskToMe(myUserId: String, tasks: List<Task>) {
        _taskToMe.value = tasks.map { task ->
            val res = TaskAssignedToMe(
                taskId = task.taskId,
                title = task.title,
                description = task.description,
                dueDate = task.dueTime,
                assignerName = task.assignerId,
                assigneePhone = "11",
                complete = isTaskCompleted(myUserId, task.assignedUsers)
            )
            notifyNewTasks(task)
            res
        }

    }

    private fun isTaskCompleted(myUserId: String, assignees: List<AssignedUser>): Boolean {
        val assignee = assignees.find { it.userId == myUserId }
        assignee?.let {
            return it.state >= 3
        }
        return false
    }

    private fun notifyNewTasks(task: Task) {
        task.assignedUsers.map {
            if (it.state == STATE_CREATED_NOT_NOTIFIED) {
                BaseApplication.notify(
                    title = "New Task",
                    message = task.title
                )
            }
        }
    }


    suspend fun subscribe(signedInUserId: String) {
        DatabaseCollectionReader(collection = TASKS_COLLECTION)
            .readObservable(Task::class.java)
            .collect { tasks ->
                onUpdateTaskEntities(
                    signedInUserId,
                    tasks.filter { isTaskAssignedToMe(signedInUserId, it.assignedUsers) })
            }
    }

    private fun isTaskAssignedToMe(myUserId: String, assignees: List<AssignedUser>): Boolean {
        return assignees.any { it.userId == myUserId }
    }


}
