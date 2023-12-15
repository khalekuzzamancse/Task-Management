package com.khalekuzzamanjustcse.taskmanagement.data_layer.notification

import com.khalekuzzamanjustcse.taskmanagement.BaseApplication
import com.khalekuzzamanjustcse.taskmanagement.data_layer.DatabaseCollectionReader
import com.khalekuzzamanjustcse.taskmanagement.data_layer.TaskEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch


object AssignedToMeTasksObserver {
    private const val TASKS_COLLECTION = "NewTaks"
    private const val STATE_CREATED_NOT_NOTIFIED = 1
   // private const val STATE_COMPLETED_NOT_NOTIFIED = 3

    private val _taskEntities = MutableStateFlow(emptyList<Task>())
    private val _taskToMe = MutableStateFlow(emptyList<TaskEntity>())
    val taskToMe = _taskToMe.asStateFlow()





    init {
        val scope = CoroutineScope(Dispatchers.Main)
        scope.launch {
            _taskEntities.collect { tasks ->
                _taskToMe.value = tasks.map { task ->
                    val res = TaskEntity(
                        id=task.taskId,
                        title = task.title,
                        description = task.description,
                        dueDate = task.dueTime,
                        assignerName = task.assignerId,
                        assigneePhone = "11"
                    )
                    notifyNewTasks(task)

                    res


                }

            }
        }


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
                _taskEntities.value =
                    tasks.filter { isTaskAssignedToMe(signedInUserId, it.assignedUsers) }
            }
    }

    private fun isTaskAssignedToMe(myUserId: String, assignees: List<AssignedUser>): Boolean {
        return assignees.any { it.userId == myUserId }
    }


}
