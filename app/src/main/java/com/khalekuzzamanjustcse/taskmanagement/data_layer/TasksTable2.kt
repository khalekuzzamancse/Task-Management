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
import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.launch
import java.io.Serializable
import java.util.UUID
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

@OptIn(ExperimentalLayoutApi::class)
@Preview
@Composable
fun TaskDemo() {
    val dummyTask = TaskEntity2(
        primaryKey = "123",
        title = "Dummy Task",
        description = "This is a dummy task for testing",
        assignerPrimaryKey = "017388",
        dueDate = "2023-01-01",
    )
    val taskToAdd = TaskToAdd(
        title = "Dummy Task",
        description = "This is a dummy task for testing",
        assignerPrimaryKey = "017388",
        dueDate = "2023-01-01",
        assignee = listOf("0123", "0134")
    )
    val scope = rememberCoroutineScope()

    FlowRow {
        Button(onClick = {
            scope.launch {
                TaskTable2().addTask(taskToAdd)

            }

        }) {
            Text(text = "Add Task")
        }
        Button(onClick = {
            scope.launch {
                TaskTable2().getTasks().collect { tasks ->
                    Log.d("TaskDemoCom:\n", "$tasks")

                }
            }

        }) {
            Text(text = "Fetch Tasks")
        }
        Button(onClick = {
            scope.launch {
                TaskTable2().updateTask(
                    dummyTask.copy(

                    )
                )
            }

        }) {
            Text(text = "Update Tasks")
        }
        Button(onClick = {
            scope.launch {

            }

        }) {
            Text(text = "Fetch Where")
        }

    }

}

data class TaskToAdd(
    val title: String,
    val description: String,
    val dueDate: String,
    val assignerPrimaryKey: String,
    val assignee: List<String>
) {
    private val taskPrimaryKey = "$title-$assignerPrimaryKey"
    fun getEntity(): TaskEntity2 {
        return TaskEntity2(
            primaryKey = taskPrimaryKey,
            title = title,
            description = description,
            dueDate = dueDate,
            assignerPrimaryKey = assignerPrimaryKey
        )
    }

    fun getTaskAssignee() = assignee.map { assignerPrimaryKey ->
        TaskAssignee(
            taskPrimaryKey = taskPrimaryKey,
            assigneePrimaryKey = assignerPrimaryKey,
            stage = "1"
        )
    }
}


data class TaskAssignee @JvmOverloads constructor(
    val taskPrimaryKey: String = "",
    val assigneePrimaryKey: String = "",
    val stage: String = ""
)


data class TaskEntity2 @JvmOverloads constructor(
    @DocumentId val primaryKey: String = "",
    val title: String = "",
    val description: String = "",
    val assignerPrimaryKey: String = "",
    val dueDate: String = "",
)

class TaskTable2 {
    companion object {
        private const val TASKS_COLLECTION = "Tasks"
        private const val TASK_ASSIGNEE_COLLECTION = "TaskAssignees"
    }

    private val db = FirebaseFirestore.getInstance()
    private val taskCollection = db.collection(TASKS_COLLECTION)
    private val taskAssignees = db.collection(TASK_ASSIGNEE_COLLECTION)


    suspend fun addTask(task: TaskToAdd) {
        val isSuccess = addToTaskCollection(task.getEntity())
        if (isSuccess) {
            task.getTaskAssignee().forEach { taskAssignee ->
                addAssignee(taskAssignee)
            }
        }
    }

    private suspend fun addToTaskCollection(task: TaskEntity2): Boolean =
        suspendCoroutine { continuation ->
            var id = UUID.randomUUID().toString()
            val taskHasNotId = task.primaryKey.isNotEmpty()
            if (taskHasNotId) {
                id = task.primaryKey
            }
            val documentReference = taskCollection.document(id)
            documentReference.set(task)
                .addOnSuccessListener { continuation.resume(true) }
                .addOnFailureListener { continuation.resume(false) }
        }

    private suspend fun addAssignee(assignee: TaskAssignee): Boolean =
        suspendCoroutine { continuation ->
            val documentReference = taskAssignees.document()
            documentReference.set(assignee)
                .addOnSuccessListener { continuation.resume(true) }
                .addOnFailureListener { continuation.resume(false) }
        }


    suspend fun updateTask(task: TaskEntity2): Boolean = suspendCoroutine { continuation ->
        val taskId = task.primaryKey
        val taskHasId = taskId.isNotBlank()
        if (taskHasId) {
            val taskDoc = taskCollection.document(taskId)
            taskDoc
                .set(task)
                .addOnSuccessListener {
                    continuation.resume(true)
                }
                .addOnFailureListener {
                    continuation.resume(false)
                }
        } else {
            continuation.resume(false)
        }
    }


    fun getTasks(): Flow<List<TaskEntity2>> = callbackFlow {
        val callback = EventListener<QuerySnapshot> { value, _ ->
            value?.let { querySnapshot ->
                val taskList = mutableListOf<TaskEntity2>()
                for (document in querySnapshot) {
                    val taskEntity = document.toObject(TaskEntity2::class.java)
                    var isNotEmpty: Boolean
                    taskEntity.apply {
                        isNotEmpty = !(title.isBlank() &&
                                primaryKey.isBlank() &&
                                description.isBlank() &&
                                assignerPrimaryKey.isBlank() &&
                                dueDate.isBlank())
                    }
                    if (isNotEmpty) {
                        taskList.add(taskEntity)
                    }
                }
                trySend(taskList)
            }
        }
        val registration = taskCollection.addSnapshotListener(callback)
        awaitClose { registration.remove() }
    }.onCompletion {}

}

