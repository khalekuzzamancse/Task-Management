package com.khalekuzzamanjustcse.taskmanagement.data_layer.task_managment

import android.util.Log
import com.google.firebase.firestore.DocumentId
import com.google.firebase.firestore.Filter
import com.khalekuzzamanjustcse.taskmanagement.BaseApplication
import com.khalekuzzamanjustcse.taskmanagement.data_layer.DatabaseCollectionReader
import com.khalekuzzamanjustcse.taskmanagement.data_layer.DatabaseDocumentReader
import com.khalekuzzamanjustcse.taskmanagement.data_layer.UserCollection
import com.khalekuzzamanjustcse.taskmanagement.ui_layer.navigation.screens.my_taskes.TaskDoer
import com.khalekuzzamanjustcse.taskmanagement.ui_layer.navigation.screens.my_taskes.TaskOwnedByMe
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch

data class Task @JvmOverloads constructor(
    @DocumentId
    val taskId: String = "",
    val title: String = "",
    val description: String = "",
    val dueTime: String = "0",
    val assignerId: String = "",
    val assignedUsers: List<AssignedUser> = emptyList()
)

data class AssignedUser @JvmOverloads constructor(
    val userId: String = "",
    val state: Int = 1,
)

fun createDummyTask(): Task {
    val assignedUsers = listOf(
        AssignedUser("01625337883", 1),
        AssignedUser("01571207787", 1)
    )

    return Task(
        taskId = "dummyTaskId",
        title = "Dummy Task",
        description = "This is a dummy task for testing",
        dueTime = "2023-01-01",
        assignerId = "01571378537",
        assignedUsers = assignedUsers
    )
}


object AssignedByMeTasksObserver {
    private const val TASKS_COLLECTION = "NewTaks"
    private const val FIELD_ASSIGNER_ID = "assignerId"
    private const val STATE_CREATED_NOT_NOTIFIED = 1
    private const val STATE_CREATED_NOTIFIED = 2
    private const val STATE_COMPLETED_NOT_NOTIFIED = 3
    private const val STATE_COMPLETED_NOTIFIED = 4

    private val _taskEntities = MutableStateFlow(emptyList<Task>())
    val _taskOwnedByMe = MutableStateFlow(emptyList<TaskOwnedByMe>())



    private suspend fun onTaskEntitiesUpdated(tasks:List<Task>) {
        _taskEntities.value = tasks
        //
        updateTaskByMe(tasks)

    }
    private suspend fun updateTaskByMe(tasks:List<Task>){
        _taskOwnedByMe.value = tasks.map { task ->
            val doers = taskDoers(task)
            val taskByMe = TaskOwnedByMe(
                taskId = task.taskId,
                title = task.title,
                description = task.description,
                dueDate = task.dueTime,
                doers = doers
            )

            taskByMe
        }
    }


    suspend fun taskDetails(taskId: String): Flow<TaskOwnedByMe> = flow {
        DatabaseDocumentReader(
            collection = TASKS_COLLECTION,
            docId = taskId
        ).readObservable(Task::class.java).collect { task ->
            val doers = taskDoers(task)
            val taskByMe = TaskOwnedByMe(
                taskId = task.taskId,
                title = task.title,
                description = task.description,
                dueDate = task.dueTime,
                doers = doers
            )
            emit(taskByMe)
        }
    }


    private suspend fun taskDoers(task: Task): List<TaskDoer> {
        return task.assignedUsers.mapNotNull {
           val doer= taskDoer(it)
            if (doer != null){
                notifyTaskComplete(doer,task)
            }

            doer
        }
    }
    private fun notifyTaskComplete(doer: TaskDoer,task: Task) {
        task.assignedUsers.map {
            if (it.state == 3) {
                BaseApplication.notify(
                    title = "Task Complete",
                    message = "${task.title} by ${doer.name}"
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




    fun notifyTasksAssigned() {
        BaseApplication.notify(
            title = "Notification Test",
            message = "Notifications From Test"
        )

    }

     suspend fun subscribe(signedInUserId: String) {
        val predicate = Filter.equalTo(FIELD_ASSIGNER_ID, signedInUserId)
        DatabaseCollectionReader(collection = TASKS_COLLECTION)
            .readObservable(Task::class.java, where = predicate).collect { tasks ->
               onTaskEntitiesUpdated(tasks)
            }
    }


}
