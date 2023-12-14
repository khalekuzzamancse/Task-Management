package com.khalekuzzamanjustcse.taskmanagement.data_layer

import android.util.Log
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.tooling.preview.Preview
import com.google.firebase.firestore.DocumentId
import com.google.firebase.firestore.Filter
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.launch


@OptIn(ExperimentalLayoutApi::class)
@Preview
@Composable
fun TaskDemo() {
    val taskToAdd = TaskToAdd(
        title = "02 Dummy Task",
        description = "02 This is a dummy task for testing",
        assignerIdentifier = "017388",
        dueDate = 12344,
        assignee = listOf("0123", "0134")
    )
    val scope = rememberCoroutineScope()

    FlowRow {
        Button(onClick = {
            scope.launch {
                TaskTable2("").createTask(taskToAdd)

            }

        }) {
            Text(text = "Add Task")
        }
        Button(onClick = {
            scope.launch {
                val tasks = TaskTable2("").getAssignedTasks("0134")
                Log.d("TaskDemoCom:", "$tasks")

            }

        }) {
            Text(text = "Fetch Tasks")
        }
        Button(onClick = {
            scope.launch {
                TaskTable2("").makeAssignedTaskNotified("RQO1v9HC9pk7JPxM5Khm")
            }

        }) {
            Text(text = "Update Tasks Stage")
        }
        Button(onClick = {
            scope.launch {
                val taskTable = TaskTable2("0123")
                val tasks = taskTable.getAssignedNotNotifiedTask()
                tasks.forEach {
                    taskTable.makeAssignedTaskNotified(it.taskAssignedId)
                }
                Log.d("NewlyAssingnedTask","$tasks")

            }

        }) {
            Text(text = "assinged  task")
        }
        Button(onClick = {
            scope.launch {
                val taskTable = TaskTable2("0123")
                val tasks = taskTable.getCompletedNotNotifiedTask()
                tasks.forEach {
                    taskTable.makeTaskCompletedTaskNotified(it.taskAssignedId)
                }

            }

        }) {
            Text(text = "Complete  not notified  task")
        }


    }

}

data class TaskToAdd(
    val title: String,
    val description: String,
    val dueDate: Long,
    val assignerIdentifier: String,
    val assignee: List<String>
) {
    private val taskPrimaryKey = "$title-$assignerIdentifier"
    fun getEntity(): TaskEntity2 {
        return TaskEntity2(
            taskId = taskPrimaryKey,
            title = title,
            description = description,
            dueTime = dueDate,
            assignerId = assignerIdentifier
        )
    }

    fun getTaskAssignee() = assignee.map { assignerPrimaryKey ->
        AssignedTask(
            taskId = taskPrimaryKey,
            assigneeId = assignerPrimaryKey,
            taskStateId = 1
        )
    }
}

data class TaskEntity2 @JvmOverloads constructor(
    @DocumentId val taskId: String = "",
    val title: String = "",
    val description: String = "",
    val dueTime: Long = 0,
    val assignerId: String = "",
)

data class AssignedTask @JvmOverloads constructor(
    @DocumentId val assignmentId: String = "",
    val taskId: String = "",
    val taskStateId: Int = 1,
    val assigneeId: String = "",
    val assignedTime: Long = 0,
    val completionTime: Long = 0,
)


data class MyAssignedTask(
    val title: String,
    val description: String,
    val assignerName: String,
    val assignerPhone: String,
    val dueDate: Long,
    val taskAssignedId: String,
) {
    companion object {
        fun toAssignedTask(assignedTask: AssignedTask, task: TaskEntity2): MyAssignedTask {
            return MyAssignedTask(
                title = task.title,
                description = task.description,
                assignerName = "--",
                assignerPhone = task.assignerId,
                dueDate = task.dueTime,
                taskAssignedId = assignedTask.assignmentId
            )
        }
    }
}


class TaskTable2(
    val myUserId: String,
) {
    companion object {
        private const val TASKS_COLLECTION = "Tasks"
        private const val COLLECTION_ASSIGNED_TASK = "AssignedTask"
        private const val ASSIGN_ID_FIELD = "assigneeId"
        private const val FIELD_STATE_ID = "taskStateId"
        private const val STATE_CREATED_NOT_NOTIFIED = 1
        private const val STATE_CREATED_NOTIFIED = 2
        private const val STATE_COMPLETED_NOT_NOTIFIED = 3
        private const val STATE_COMPLETED_NOTIFIED = 4

    }

    private val db = FirebaseFirestore.getInstance()
    private val taskCollection = db.collection(TASKS_COLLECTION)
    private val assignedTaskCollection = db.collection(COLLECTION_ASSIGNED_TASK)

    private val databaseCRUD = DatabaseCRUD()


    suspend fun createTask(task: TaskToAdd) {
        val entity = task.getEntity()
        val isSuccess = databaseCRUD.create(
            collectionName = TASKS_COLLECTION,
            entity = entity,
            docID = entity.taskId
        )
        if (isSuccess) {
            task.getTaskAssignee().forEach { taskAssignee ->
                addAssignee(taskAssignee)
            }
        }
    }

    suspend fun getAssignedNotNotifiedTask(): List<MyAssignedTask> {
        val ref = getAssignTaskRef().filter { it.taskStateId == STATE_CREATED_NOT_NOTIFIED }
        return getAssignTask(ref)
    }

    suspend fun getCompletedNotNotifiedTask(): List<MyAssignedTask> {
        val ref = getAssignTaskRef().filter { it.taskStateId == STATE_COMPLETED_NOT_NOTIFIED }
        return getAssignTask(ref)
    }

    suspend fun makeAssignedTaskNotified(assignedTaskId: String): Boolean {
        return databaseCRUD.update(
            COLLECTION_ASSIGNED_TASK,
            Updater(
                docID = assignedTaskId,
                field = FIELD_STATE_ID,
                value = STATE_CREATED_NOTIFIED
            )
        )
    }

    suspend fun makeTaskCompleted(assignedTaskId: String): Boolean {
        return databaseCRUD.update(
            COLLECTION_ASSIGNED_TASK,
            Updater(
                docID = assignedTaskId,
                field = FIELD_STATE_ID,
                value = STATE_COMPLETED_NOT_NOTIFIED
            )
        )
    }

    suspend fun makeTaskCompletedTaskNotified(assignedTaskId: String): Boolean {
        return databaseCRUD.update(
            COLLECTION_ASSIGNED_TASK,
            Updater(
                docID = assignedTaskId,
                field = FIELD_STATE_ID,
                value = STATE_COMPLETED_NOTIFIED
            )
        )
    }


    private suspend fun addAssignee(assignee: AssignedTask): Boolean {
        return databaseCRUD.create(
            COLLECTION_ASSIGNED_TASK, assignee
        )

    }

    suspend fun getAssignTaskRef(): List<AssignedTask> {
        return databaseCRUD.read(
            collectionName = COLLECTION_ASSIGNED_TASK,
            predicate = Filter.equalTo(ASSIGN_ID_FIELD, myUserId)
        )
    }

    suspend fun getAssignTask(refs: List<AssignedTask>): List<MyAssignedTask> {
        val tasks = mutableListOf<MyAssignedTask>()
        refs.forEach { taskAssignee ->
            val task = databaseCRUD.read<TaskEntity2>(
                collectionName = TASKS_COLLECTION,
                docId = taskAssignee.taskId
            )
            if (task != null) tasks.add(MyAssignedTask.toAssignedTask(taskAssignee, task))
        }
        return tasks
    }

    suspend fun getAssignedTasks(signedUserPhone: String): List<MyAssignedTask> {
        val tasks = mutableListOf<MyAssignedTask>()
        val refs = databaseCRUD.read<AssignedTask>(
            collectionName = COLLECTION_ASSIGNED_TASK,
            predicate = Filter.equalTo(ASSIGN_ID_FIELD, signedUserPhone)
        )
        refs.forEach { taskAssignee ->
            val task = databaseCRUD.read<TaskEntity2>(
                collectionName = TASKS_COLLECTION,
                docId = taskAssignee.taskId
            )
            if (task != null) tasks.add(MyAssignedTask.toAssignedTask(taskAssignee, task))
        }
        return tasks
    }

}

