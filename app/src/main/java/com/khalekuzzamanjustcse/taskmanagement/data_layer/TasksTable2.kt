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
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.tasks.await
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
        title = "02 Dummy Task",
        description = "02 This is a dummy task for testing",
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
                val tasks = TaskTable2().getAssignedTasks("0134")
                Log.d("TaskDemoCom:\n", "$tasks")

            }

        }) {
            Text(text = "Fetch Tasks")
        }
        Button(onClick = {
            scope.launch {
                TaskTable2().updateTaskStage(
                    taskAssignee = TaskAssignee(
                        documentId = "Odtav6tnVTFmsfYtOZaz",
                        assigneePrimaryKey = "0123",
                        stage = "2",
                        taskPrimaryKey = "Dummy Task-017388"
                    ),
                    stage = "4"
                )
            }

        }) {
            Text(text = "Update Tasks Stage")
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
    @DocumentId val documentId: String = "",
    val taskPrimaryKey: String = "",
    val assigneePrimaryKey: String = "",
    val stage: String = ""
)


data class AssignedTask(
    val title: String,
    val description: String,
    val assignerName: String,
    val assignerPhone: String,
    val dueDate: String,
    val taskAssignedId: String,
) {
    companion object {
        fun toAssignedTask(taskAssignee: TaskAssignee, task: TaskEntity2): AssignedTask {
            return AssignedTask(
                title = task.title,
                description = task.description,
                assignerName = "--",
                assignerPhone = task.assignerPrimaryKey,
                dueDate = task.dueDate,
                taskAssignedId = taskAssignee.documentId
            )
        }
    }
}

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
    private val taskAssigneesCollection = db.collection(TASK_ASSIGNEE_COLLECTION)


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
            val documentReference = taskAssigneesCollection.document()
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

    suspend fun updateTaskStage(taskAssignee: TaskAssignee, stage: String): Boolean =
        suspendCoroutine { continuation ->
            if (taskAssignee.documentId.isNotBlank()) {
                val taskDoc = taskAssigneesCollection.document(taskAssignee.documentId)
                taskDoc
                    .set(taskAssignee.copy(stage = stage))
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

    suspend fun getAssignedTasks(signedUserPhone: String): List<AssignedTask> {
        val tasks = mutableListOf<AssignedTask>()
        val refs = getAssignedTaskReferences(signedUserPhone)
        refs.forEach { taskAssignee ->
            val task = getTasks(taskAssignee.taskPrimaryKey)
            if (task != null) tasks.add(AssignedTask.toAssignedTask(taskAssignee, task))
        }
        return tasks
    }

    private suspend fun getTasks(taskPK: String): TaskEntity2? {
        return try {
            val documentSnapshot = taskCollection.document(taskPK)
                .get()
                .await()
            if (documentSnapshot.exists())
                documentSnapshot.toObject(TaskEntity2::class.java)
            else null
        } catch (e: Exception) {
            null
        }
    }


    private suspend fun getAssignedTaskReferences(signedUserPhone: String): List<TaskAssignee> {
        return suspendCancellableCoroutine { continuation ->
            val callback = EventListener<QuerySnapshot> { value, error ->
                if (error != null) {
                    // Handle the error
                    continuation.resumeWith(Result.failure(error))
                    return@EventListener
                }

                value?.let { querySnapshot ->
                    val assignedTasksRef = mutableListOf<TaskAssignee>()
                    for (document in querySnapshot) {
                        val taskEntity = document.toObject(TaskAssignee::class.java)
                        assignedTasksRef.add(taskEntity)
                    }
                    continuation.resume(assignedTasksRef)
                }
            }

            val query = taskAssigneesCollection
                .whereEqualTo("assigneePrimaryKey", signedUserPhone)

            val registration = query.addSnapshotListener(callback)

            // When the coroutine is cancelled, remove the snapshot listener
            continuation.invokeOnCancellation {
                registration.remove()
            }
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

